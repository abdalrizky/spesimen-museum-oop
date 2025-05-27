package com.spesimenmuseum.service;

import com.spesimenmuseum.model.Specimen;

import java.util.List;

public interface ISpecimenService {
    boolean addSpecimen(String commonName, String scientificName, String type, String preservationMethod, int quantity, String description);
    List<Specimen> getAllSpecimens();
    Specimen getSpecimenById(int id);
    boolean updateSpecimen(int id, String commonName, String scientificName, String type, String preservationMethod, int quantity, String description, String condition);
    boolean deleteSpecimen(int id);
    boolean examineSpecimen(int id, String examinationResult);
}
