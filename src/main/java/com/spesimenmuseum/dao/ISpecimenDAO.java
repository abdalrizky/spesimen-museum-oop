package com.spesimenmuseum.dao;

import com.spesimenmuseum.model.Specimen;

import java.sql.SQLException;
import java.util.List;

public interface ISpecimenDAO {
    boolean save(Specimen specimen) throws SQLException;
    Specimen findById(int id) throws SQLException;
    List<Specimen> findAll() throws SQLException;
    boolean update(Specimen specimen) throws SQLException;
    boolean updateExamination(Specimen specimen) throws SQLException;
    boolean delete(int id) throws SQLException;
}
