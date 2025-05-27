package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.IUserDAO;
import com.spesimenmuseum.model.Role;
import com.spesimenmuseum.model.User;

import java.sql.*;

public class UserDAO implements IUserDAO {
    private final RoleDAO roleDAO;

    public UserDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public int saveCredential(User user) throws SQLException {
        String sql = "INSERT INTO credentials (role_id, email, phone_number, username, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (user.getRole() == null || user.getRole().getId() <= 0) {
                throw new SQLException("Role ID tidak valid untuk user: " + user.getUsername());
            }
            pstmt.setInt(1, user.getRole().getId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhoneNumber());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPasswordHash());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan credential user, tidak ada baris yang terpengaruh.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Gagal menyimpan credential user, tidak mendapatkan ID.");
                }
            }
        }
    }

    @Override
    public User findBaseByUsername(String username) {
        String sql = "SELECT id, role_id, email, phone_number, username, password FROM credentials WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToBaseUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari user berdasarkan username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateCredentialDetails(User user) throws SQLException {
        String sql = "UPDATE credentials SET email = ?, phone_number = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setInt(3, user.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    private User mapToBaseUser(ResultSet rs) throws SQLException {
        User baseUser = new User() {
            private String tempFullName;
            @Override public String getFullName() { return tempFullName; }
            @Override public void setFullName(String fullName) { this.tempFullName = fullName;}
        };
        baseUser.setId(rs.getInt("id"));
        baseUser.setUsername(rs.getString("username"));
        baseUser.setPasswordHash(rs.getString("password"));
        baseUser.setEmail(rs.getString("email"));
        baseUser.setPhoneNumber(rs.getString("phone_number"));

        Role role = roleDAO.findById(rs.getInt("role_id"));
        if (role == null) {
            System.err.println("Peringatan: Role tidak ditemukan untuk role_id: " + rs.getInt("role_id") + " pada user: " + baseUser.getUsername());
        }
        baseUser.setRole(role);
        return baseUser;
    }
}
