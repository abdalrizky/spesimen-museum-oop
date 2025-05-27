package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Role;

import java.sql.SQLException;

public interface IRoleDAO {
    Role findById(int id) throws SQLException;
    Role findByName(String name) throws SQLException;
}
