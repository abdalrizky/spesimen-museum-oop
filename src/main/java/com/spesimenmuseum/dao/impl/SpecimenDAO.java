package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.ISpecimenDAO;
import com.spesimenmuseum.model.Specimen;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpecimenDAO implements ISpecimenDAO {
    @Override
    public boolean save(Specimen specimen) throws SQLException {
        String sql = "INSERT INTO specimens (common_name, scientific_name, type, preservation_method, quantity, description, `condition`, examination_result, entry_at, examined_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            mapSpecimenToStatementForInsert(specimen, pstmt);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        specimen.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public Specimen findById(int id) throws SQLException {
        String sql = "SELECT * FROM specimens WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToSpecimen(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Specimen> findAll() throws SQLException {
        List<Specimen> specimens = new ArrayList<>();
        String sql = "SELECT * FROM specimens";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                specimens.add(mapToSpecimen(rs));
            }
        }
        return specimens;
    }

    @Override
    public boolean update(Specimen specimen) throws SQLException {
        String sql = "UPDATE specimens SET common_name = ?, scientific_name = ?, type = ?, preservation_method = ?, " +
                "quantity = ?, description = ?, `condition` = ?, examination_result = ?, entry_at = ?, examined_at = ? " +
                "WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            mapSpecimenToStatementForUpdate(specimen, pstmt);
            pstmt.setInt(11, specimen.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateExamination(Specimen specimen) throws SQLException {
        String sql = "UPDATE specimens SET `condition` = ?, examination_result = ?, examined_at = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, specimen.getCondition());
            pstmt.setString(2, specimen.getExaminationResult());
            if (specimen.getExaminedAt() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(specimen.getExaminedAt()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }
            pstmt.setInt(4, specimen.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM specimens WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private void mapSpecimenToStatementForInsert(Specimen specimen, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, specimen.getCommonName());
        pstmt.setString(2, specimen.getScientificName());
        pstmt.setString(3, specimen.getType());
        pstmt.setString(4, specimen.getPreservationMethod());
        pstmt.setInt(5, specimen.getQuantity());
        pstmt.setString(6, specimen.getDescription());
        pstmt.setString(7, specimen.getCondition() != null ? specimen.getCondition() : "Baik");
        pstmt.setString(8, specimen.getExaminationResult() != null ? specimen.getExaminationResult() : "-");
        if (specimen.getEntryAt() != null) {
            pstmt.setTimestamp(9, Timestamp.valueOf(specimen.getEntryAt()));
        } else {
            pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        }
        if (specimen.getExaminedAt() != null) {
            pstmt.setTimestamp(10, Timestamp.valueOf(specimen.getExaminedAt()));
        } else {
            pstmt.setNull(10, Types.TIMESTAMP);
        }
    }

    private void mapSpecimenToStatementForUpdate(Specimen specimen, PreparedStatement pstmt) throws SQLException {
        mapSpecimenToStatementForInsert(specimen, pstmt);
    }

    private Specimen mapToSpecimen(ResultSet rs) throws SQLException {
        Specimen specimen = new Specimen();
        specimen.setId(rs.getInt("id"));
        specimen.setCommonName(rs.getString("common_name"));
        specimen.setScientificName(rs.getString("scientific_name"));
        specimen.setType(rs.getString("type"));
        specimen.setPreservationMethod(rs.getString("preservation_method"));
        specimen.setQuantity(rs.getInt("quantity"));
        specimen.setDescription(rs.getString("description"));
        specimen.setCondition(rs.getString("condition"));
        specimen.setExaminationResult(rs.getString("examination_result"));

        Timestamp entryAtTimestamp = rs.getTimestamp("entry_at");
        if (entryAtTimestamp != null) {
            specimen.setEntryAt(entryAtTimestamp.toLocalDateTime());
        }

        Timestamp examinedAtTimestamp = rs.getTimestamp("examined_at");
        if (examinedAtTimestamp != null) {
            specimen.setExaminedAt(examinedAtTimestamp.toLocalDateTime());
        } else {
            specimen.setExaminedAt(null);
        }
        return specimen;
    }
}
