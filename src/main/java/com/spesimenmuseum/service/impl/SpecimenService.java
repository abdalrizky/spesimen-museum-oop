package com.spesimenmuseum.service.impl;

import com.spesimenmuseum.dao.impl.SpecimenDAO;
import com.spesimenmuseum.model.Specimen;
import com.spesimenmuseum.service.ISpecimenService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SpecimenService implements ISpecimenService {
    private final SpecimenDAO specimenDAO;

    public SpecimenService(SpecimenDAO specimenDAO) {
        this.specimenDAO = specimenDAO;
    }

    @Override
    public boolean addSpecimen(String commonName, String scientificName, String type, String preservationMethod, int quantity, String description) {
        if (commonName == null || commonName.trim().isEmpty() ||
                scientificName == null || scientificName.trim().isEmpty()) {
            System.err.println("Nama umum dan nama ilmiah spesimen tidak boleh kosong.");
            return false;
        }
        if (quantity <= 0) {
            System.err.println("Jumlah spesimen harus lebih dari 0.");
            return false;
        }

        Specimen specimen = new Specimen(commonName, scientificName, type, preservationMethod, quantity, description, "Baik", LocalDateTime.now());
        try {
            return specimenDAO.save(specimen);
        } catch (SQLException e) {
            System.err.println("Error saat menambah spesimen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Specimen> getAllSpecimens() {
        try {
            return specimenDAO.findAll();
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua spesimen: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Specimen getSpecimenById(int id) {
        if (id <= 0) return null;
        try {
            return specimenDAO.findById(id);
        } catch (SQLException e) {
            System.err.println("Error saat mencari spesimen berdasarkan ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateSpecimen(int id, String commonName, String scientificName, String type, String preservationMethod, int quantity, String description, String condition) {
        Specimen specimen = getSpecimenById(id);
        if (specimen == null) {
            System.err.println("Spesimen dengan ID " + id + " tidak ditemukan.");
            return false;
        }
        if (commonName == null || commonName.trim().isEmpty() ||
                scientificName == null || scientificName.trim().isEmpty()) {
            System.err.println("Nama umum dan nama ilmiah spesimen tidak boleh kosong.");
            return false;
        }
        if (quantity <= 0) {
            System.err.println("Jumlah spesimen harus lebih dari 0.");
            return false;
        }

        specimen.setCommonName(commonName);
        specimen.setScientificName(scientificName);
        specimen.setType(type);
        specimen.setPreservationMethod(preservationMethod);
        specimen.setQuantity(quantity);
        specimen.setDescription(description);
        specimen.setCondition(condition);

        try {
            return specimenDAO.update(specimen);
        } catch (SQLException e) {
            System.err.println("Error saat mengupdate spesimen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSpecimen(int id) {
        if (id <= 0) return false;
        try {
            return specimenDAO.delete(id);
        } catch (SQLException e) {
            System.err.println("Error saat menghapus spesimen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean examineSpecimen(int id, String examinationResult) {
        Specimen specimen = getSpecimenById(id);
        if (specimen == null) {
            System.err.println("Spesimen dengan ID " + id + " tidak ditemukan untuk pemeriksaan.");
            return false;
        }
        if (examinationResult == null || examinationResult.trim().isEmpty()){
            System.err.println("Hasil pemeriksaan tidak boleh kosong.");
            return false;
        }

        specimen.setExaminationResult(examinationResult);
        specimen.setCondition("Selesai Diperiksa");
        specimen.setExaminedAt(LocalDateTime.now());
        try {
            return specimenDAO.updateExamination(specimen);
        } catch (SQLException e) {
            System.err.println("Error saat memeriksa spesimen: " + e.getMessage());
            return false;
        }
    }
}
