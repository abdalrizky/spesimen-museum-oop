package com.spesimenmuseum.model;

public class Specialist extends User {
    private int specialistId;
    private String fullName;
    private String skill;
    private String institution;

    public Specialist(String username, String passwordHash, String email, String phoneNumber, Role role,
                      String fullName, String skill, String institution) {
        super(username, passwordHash, email, phoneNumber, role);
        this.fullName = fullName;
        this.skill = skill;
        this.institution = institution;
    }

    public int getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
