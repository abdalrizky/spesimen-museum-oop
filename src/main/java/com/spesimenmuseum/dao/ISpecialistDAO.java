package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Specialist;

import java.sql.SQLException;
import java.util.List;

public interface ISpecialistDAO {
    boolean save(Specialist specialist) throws SQLException;
    Specialist findByCredentialId(int credentialId) throws SQLException;
    Specialist findById(int specialistId) throws SQLException;
    boolean update(Specialist specialist) throws SQLException;
    List<Specialist> findAllSpecifics() throws SQLException;
}
