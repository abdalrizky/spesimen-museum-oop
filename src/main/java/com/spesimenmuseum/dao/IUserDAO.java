package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.User;

import java.sql.SQLException;

public interface IUserDAO {
    int saveCredential(User user) throws SQLException;
    User findBaseByUsername(String username) throws SQLException;
    boolean updateCredentialDetails(User user) throws SQLException;
}
