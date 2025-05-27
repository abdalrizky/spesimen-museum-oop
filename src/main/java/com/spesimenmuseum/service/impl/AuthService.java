package com.spesimenmuseum.service.impl;

import com.spesimenmuseum.dao.impl.*;
import com.spesimenmuseum.model.*;
import com.spesimenmuseum.service.IAuthService;
import com.spesimenmuseum.util.PasswordUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthService implements IAuthService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final EmployeeDAO employeeDAO;
    private final SpecialistDAO specialistDAO;
    private final VisitorDAO visitorDAO;

    public AuthService(UserDAO userDAO, RoleDAO roleDAO, EmployeeDAO employeeDAO, SpecialistDAO specialistDAO, VisitorDAO visitorDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.employeeDAO = employeeDAO;
        this.specialistDAO = specialistDAO;
        this.visitorDAO = visitorDAO;
    }

    @Override
    public User login(String username, String password) {
        User baseUserCredentials = userDAO.findBaseByUsername(username);
        if (baseUserCredentials != null && PasswordUtil.checkPassword(password, baseUserCredentials.getPasswordHash())) {
            try {
                Role userRole = baseUserCredentials.getRole();
                if (userRole == null) {
                    System.err.println("Login Gagal: Role tidak ditemukan untuk user " + username);
                    return null;
                }

                switch (userRole.getName().toLowerCase()) {
                    case "employee":
                        Employee empSpecifics = employeeDAO.findByCredentialId(baseUserCredentials.getId());
                        if (empSpecifics != null) {
                            Employee employee = new Employee(baseUserCredentials.getUsername(), baseUserCredentials.getPasswordHash(),
                                    baseUserCredentials.getEmail(), baseUserCredentials.getPhoneNumber(), userRole,
                                    empSpecifics.getFullName(), empSpecifics.getPosition(), empSpecifics.getJoinedAt());
                            employee.setId(baseUserCredentials.getId());
                            employee.setEmployeeId(empSpecifics.getEmployeeId());
                            return employee;
                        }
                        break;
                    case "specialist":
                        Specialist specSpecifics = specialistDAO.findByCredentialId(baseUserCredentials.getId());
                        if (specSpecifics != null) {
                            Specialist specialist = new Specialist(baseUserCredentials.getUsername(), baseUserCredentials.getPasswordHash(),
                                    baseUserCredentials.getEmail(), baseUserCredentials.getPhoneNumber(), userRole,
                                    specSpecifics.getFullName(), specSpecifics.getSkill(), specSpecifics.getInstitution());
                            specialist.setId(baseUserCredentials.getId());
                            specialist.setSpecialistId(specSpecifics.getSpecialistId());
                            return specialist;
                        }
                        break;
                    case "visitor":
                        Visitor visSpecifics = visitorDAO.findByCredentialId(baseUserCredentials.getId());
                        if (visSpecifics != null) {
                            Visitor visitor = new Visitor(baseUserCredentials.getUsername(), baseUserCredentials.getPasswordHash(),
                                    baseUserCredentials.getEmail(), baseUserCredentials.getPhoneNumber(), userRole,
                                    visSpecifics.getFullName(), visSpecifics.getResidence());
                            visitor.setId(baseUserCredentials.getId());
                            visitor.setVisitorId(visSpecifics.getVisitorId());
                            return visitor;
                        }
                        break;
                    default:
                        return null;
                }
                System.err.println("Login Gagal: Data spesifik tidak ditemukan untuk user " + username + " dengan role " + userRole.getName());
            } catch (SQLException e) {
                System.err.println("Login Gagal: Database error saat mengambil detail user: " + e.getMessage());
                return null;
            }
        }
        System.err.println("Login Gagal: Username atau password salah untuk " + username);
        return null;
    }

    @Override
    public boolean registerEmployee(String username, String password, String email, String phone, String fullName, String position) {
        if (userDAO.findBaseByUsername(username) != null) {
            System.err.println("Registrasi Gagal: Username '" + username + "' sudah terdaftar.");
            return false;
        }
        Role employeeRole = roleDAO.findByName("employee");
        if (employeeRole == null) {
            System.err.println("Registrasi Gagal: Role 'employee' tidak ditemukan di database.");
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        Employee employee = new Employee(username, hashedPassword, email, phone, employeeRole, fullName, position, LocalDateTime.now());

        try {
            int credentialId = userDAO.saveCredential(employee);
            if (credentialId == -1) return false;

            employee.setId(credentialId);
            return employeeDAO.save(employee);
        } catch (SQLException e) {
            System.err.println("Registrasi Gagal: Database error. " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registerSpecialist(String username, String password, String email, String phone, String fullName, String skill, String institution) {
        if (userDAO.findBaseByUsername(username) != null) {
            System.err.println("Registrasi Gagal: Username '" + username + "' sudah terdaftar.");
            return false;
        }
        Role specialistRole = roleDAO.findByName("specialist");
        if (specialistRole == null) {
            System.err.println("Registrasi Gagal: Role 'specialist' tidak ditemukan di database.");
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        Specialist specialist = new Specialist(username, hashedPassword, email, phone, specialistRole, fullName, skill, institution);

        try {
            int credentialId = userDAO.saveCredential(specialist);
            if (credentialId == -1) return false;

            specialist.setId(credentialId);
            return specialistDAO.save(specialist);
        } catch (SQLException e) {
            System.err.println("Registrasi Gagal: Database error. " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registerVisitor(String username, String password, String email, String phone, String fullName, String residence) {
        if (userDAO.findBaseByUsername(username) != null) {
            System.err.println("Registrasi Gagal: Username '" + username + "' sudah terdaftar.");
            return false;
        }
        Role visitorRole = roleDAO.findByName("visitor");
        if (visitorRole == null) {
            System.err.println("Registrasi Gagal: Role 'visitor' tidak ditemukan di database.");
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        Visitor visitor = new Visitor(username, hashedPassword, email, phone, visitorRole, fullName, residence);

        try {
            int credentialId = userDAO.saveCredential(visitor);
            if (credentialId == -1) return false;

            visitor.setId(credentialId);
            return visitorDAO.save(visitor);
        } catch (SQLException e) {
            System.err.println("Registrasi Gagal: Database error. " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateVisitorProfile(Visitor visitor) {
        try {
            boolean credUpdated = userDAO.updateCredentialDetails(visitor);
            boolean profileUpdated = visitorDAO.update(visitor);
            return credUpdated && profileUpdated;
        } catch (SQLException e) {
            System.err.println("Update Profil Gagal: Database error. " + e.getMessage());
            return false;
        }
    }
}
