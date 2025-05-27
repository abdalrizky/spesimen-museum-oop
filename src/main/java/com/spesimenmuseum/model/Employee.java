package com.spesimenmuseum.model;

import java.time.LocalDateTime;

public class Employee extends User {
    private int employeeId;
    private String fullName;
    private String position;
    private LocalDateTime joinedAt;

    public Employee() {}

    public Employee(String username, String passwordHash, String email, String phoneNumber, Role role,
                    String fullName, String position, LocalDateTime joinedAt) {
        super(username, passwordHash, email, phoneNumber, role);
        this.fullName = fullName;
        this.position = position;
        this.joinedAt = joinedAt;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}