package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Visitor;

import java.sql.SQLException;
import java.util.List;

public interface IVisitorDAO {
    boolean save(Visitor visitor) throws SQLException;
    Visitor findByCredentialId(int credentialId) throws SQLException;
    Visitor findById(int visitorId) throws SQLException;
    boolean update(Visitor visitor) throws SQLException;
    List<Visitor> findAllSpecifics() throws SQLException;
}
