package com.spesimenmuseum.model;

public class Visitor extends User {
    private int visitorId;
    private String fullName;
    private String residence;

    public Visitor() {}

    public Visitor(String username, String passwordHash, String email, String phoneNumber, Role role,
                   String fullName, String residence) {
        super(username, passwordHash, email, phoneNumber, role);
        this.fullName = fullName;
        this.residence = residence;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
}
