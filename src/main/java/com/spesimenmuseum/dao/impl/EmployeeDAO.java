package com.spesimenmuseum.dao.impl;

import com.spesimenmuseum.config.DatabaseConfig;
import com.spesimenmuseum.dao.IEmployeeDAO;
import com.spesimenmuseum.model.Employee;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {
    @Override
    public boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (credential_id, full_name, position, joined_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (employee.getId() <= 0) { // employee.getId() adalah credential_id
                throw new SQLException("Credential ID tidak valid untuk menyimpan data employee.");
            }
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getFullName());
            pstmt.setString(3, employee.getPosition());
            if (employee.getJoinedAt() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(employee.getJoinedAt()));
            } else {
                pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // Default jika null
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setEmployeeId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public Employee findByCredentialId(int credentialId) throws SQLException {
        String sql = "SELECT id, full_name, position, joined_at FROM employees WHERE credential_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, credentialId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToEmployeeSpecifics(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Employee findById(int employeeId) throws SQLException {
        String sql = "SELECT id, full_name, position, joined_at, credential_id FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employee emp = mapToEmployeeSpecifics(rs);
                    // Set juga credential_id jika perlu untuk referensi, meskipun tidak bagian dari User base saat ini
                    // emp.setId(rs.getInt("credential_id")); // Ini akan menimpa id dari User, hati-hati
                    return emp;
                }
            }
        }
        return null;
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET full_name = ?, position = ?, joined_at = ? WHERE id = ?"; // id di sini adalah PK tabel employees
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getFullName());
            pstmt.setString(2, employee.getPosition());
            if (employee.getJoinedAt() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(employee.getJoinedAt()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }
            pstmt.setInt(4, employee.getEmployeeId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Employee> findAllSpecifics() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, full_name, position, joined_at FROM employees";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                employees.add(mapToEmployeeSpecifics(rs));
            }
        }
        return employees;
    }

    private Employee mapToEmployeeSpecifics(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("id"));
        employee.setFullName(rs.getString("full_name"));
        employee.setPosition(rs.getString("position"));
        Timestamp joinedAtTimestamp = rs.getTimestamp("joined_at");
        if (joinedAtTimestamp != null) {
            employee.setJoinedAt(joinedAtTimestamp.toLocalDateTime());
        }
        return employee;
    }
}
