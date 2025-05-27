package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.IVisitorDAO;
import com.spesimenmuseum.model.Visitor;

import java.sql.*;

public class VisitorDAO implements IVisitorDAO {
    @Override
    public boolean save(Visitor visitor) throws SQLException {
        String sql = "INSERT INTO visitors (credential_id, full_name, residence) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (visitor.getId() <= 0) {
                throw new SQLException("Credential ID tidak valid untuk menyimpan data visitor.");
            }
            pstmt.setInt(1, visitor.getId());
            pstmt.setString(2, visitor.getFullName());
            pstmt.setString(3, visitor.getResidence());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        visitor.setVisitorId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public Visitor findByCredentialId(int credentialId) throws SQLException {
        String sql = "SELECT id, full_name, residence FROM visitors WHERE credential_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, credentialId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToVisitorSpecifics(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Visitor findById(int visitorId) throws SQLException {
        String sql = "SELECT id, full_name, residence FROM visitors WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToVisitorSpecifics(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean update(Visitor visitor) throws SQLException {
        String sql = "UPDATE visitors SET full_name = ?, residence = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visitor.getFullName());
            pstmt.setString(2, visitor.getResidence());
            pstmt.setInt(3, visitor.getVisitorId());
            return pstmt.executeUpdate() > 0;
        }
    }

    private Visitor mapToVisitorSpecifics(ResultSet rs) throws SQLException {
        Visitor visitor = new Visitor();
        visitor.setVisitorId(rs.getInt("id"));
        visitor.setFullName(rs.getString("full_name"));
        visitor.setResidence(rs.getString("residence"));
        return visitor;
    }
}
