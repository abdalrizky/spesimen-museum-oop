package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.IRoleDAO;
import com.spesimenmuseum.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements IRoleDAO {
    @Override
    public Role findById(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToRole(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Role findByName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToRole(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by name: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                roles.add(mapToRole(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all roles: " + e.getMessage());
        }
        return roles;
    }

    private Role mapToRole(ResultSet rs) throws SQLException {
        return new Role(rs.getInt("id"), rs.getString("name"));
    }
}
