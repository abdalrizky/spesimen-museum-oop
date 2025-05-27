package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDAO {
    boolean save(Employee employee) throws SQLException;
    Employee findByCredentialId(int credentialId) throws SQLException;
    Employee findById(int employeeId) throws SQLException;
    boolean update(Employee employee) throws SQLException;
    List<Employee> findAllSpecifics() throws SQLException;
}
