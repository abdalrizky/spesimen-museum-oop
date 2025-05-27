package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.User;

import java.sql.SQLException;

public interface IUserDAO {
    int saveCredential(User user) throws SQLException;
    User findBaseByUsername(String username) throws SQLException;
    User findBaseByCredentialId(int credentialId) throws SQLException;
    boolean updateCredentialDetails(User user) throws SQLException;
    boolean updatePassword(int userId, String newPasswordHash) throws SQLException;
}
