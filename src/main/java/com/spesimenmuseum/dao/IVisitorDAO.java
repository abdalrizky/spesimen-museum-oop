package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Visitor;

import java.sql.SQLException;

public interface IVisitorDAO {
    boolean save(Visitor visitor) throws SQLException;
    Visitor findByCredentialId(int credentialId) throws SQLException;
    Visitor findById(int visitorId) throws SQLException;
    boolean update(Visitor visitor) throws SQLException;
}
