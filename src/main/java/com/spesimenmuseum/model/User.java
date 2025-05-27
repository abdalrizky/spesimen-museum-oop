package com.spesimenmuseum.model;

public abstract class User {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private Role role;

    public User() {}

    public User(String username, String passwordHash, String email, String phoneNumber, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public abstract String getFullName();

    public abstract void setFullName(String fullName);

    public void displaySelfDataHeader() {
        System.out.println("Username          : " + getUsername());
        System.out.println("Email             : " + getEmail());
        System.out.println("Nomor Telepon     : " + getPhoneNumber());
        System.out.println("Role              : " + (getRole() != null ? getRole().getName() : "N/A"));
    }
}
