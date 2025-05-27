package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.ISpecialistDAO;
import com.spesimenmuseum.model.Specialist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialistDAO implements ISpecialistDAO {
    @Override
    public boolean save(Specialist specialist) throws SQLException {
        String sql = "INSERT INTO specialists (credential_id, full_name, skill, institution) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (specialist.getId() <= 0) { // specialist.getId() adalah credential_id
                throw new SQLException("Credential ID tidak valid untuk menyimpan data specialist.");
            }
            pstmt.setInt(1, specialist.getId());
            pstmt.setString(2, specialist.getFullName());
            pstmt.setString(3, specialist.getSkill());
            pstmt.setString(4, specialist.getInstitution());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        specialist.setSpecialistId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public Specialist findByCredentialId(int credentialId) throws SQLException {
        String sql = "SELECT id, full_name, skill, institution FROM specialists WHERE credential_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, credentialId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToSpecialistSpecifics(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Specialist findById(int specialistId) throws SQLException {
        String sql = "SELECT id, full_name, skill, institution FROM specialists WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, specialistId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToSpecialistSpecifics(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean update(Specialist specialist) throws SQLException {
        String sql = "UPDATE specialists SET full_name = ?, skill = ?, institution = ? WHERE id = ?"; // id adalah PK tabel specialists
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, specialist.getFullName());
            pstmt.setString(2, specialist.getSkill());
            pstmt.setString(3, specialist.getInstitution());
            pstmt.setInt(4, specialist.getSpecialistId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Specialist> findAllSpecifics() throws SQLException {
        List<Specialist> specialists = new ArrayList<>();
        String sql = "SELECT id, full_name, skill, institution FROM specialists";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                specialists.add(mapToSpecialistSpecifics(rs));
            }
        }
        return specialists;
    }

    private Specialist mapToSpecialistSpecifics(ResultSet rs) throws SQLException {
        Specialist specialist = new Specialist();
        specialist.setSpecialistId(rs.getInt("id"));
        specialist.setFullName(rs.getString("full_name"));
        specialist.setSkill(rs.getString("skill"));
        specialist.setInstitution(rs.getString("institution"));
        return specialist;
    }
}
