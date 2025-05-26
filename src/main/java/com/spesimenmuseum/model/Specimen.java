package com.spesimenmuseum.model;

import java.time.LocalDateTime;

public class Specimen {
    private int id;
    private String commonName;
    private String scientificName;
    private String type;
    private String preservationMethod;
    private int quantity;
    private String description;
    private String condition;
    private String examinationResult;
    private LocalDateTime entryAt;
    private LocalDateTime examinedAt;

    public Specimen(String commonName, String scientificName, String type, String preservationMethod,
                    int quantity, String description, String condition, LocalDateTime entryAt) {
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.type = type;
        this.preservationMethod = preservationMethod;
        this.quantity = quantity;
        this.description = description;
        this.condition = condition;
        this.entryAt = entryAt;
        this.examinationResult = "-";
        this.examinedAt = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreservationMethod() {
        return preservationMethod;
    }

    public void setPreservationMethod(String preservationMethod) {
        this.preservationMethod = preservationMethod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getExaminationResult() {
        return examinationResult;
    }

    public void setExaminationResult(String examinationResult) {
        this.examinationResult = examinationResult;
    }

    public LocalDateTime getEntryAt() {
        return entryAt;
    }

    public void setEntryAt(LocalDateTime entryAt) {
        this.entryAt = entryAt;
    }

    public LocalDateTime getExaminedAt() {
        return examinedAt;
    }

    public void setExaminedAt(LocalDateTime examinedAt) {
        this.examinedAt = examinedAt;
    }
}
