package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface IRoleDAO {
    Role findById(int id) throws SQLException;
    Role findByName(String name) throws SQLException;
    List<Role> findAll() throws SQLException;
}
