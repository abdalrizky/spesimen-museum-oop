package com.spesimenmuseum.service;

import com.spesimenmuseum.model.User;
import com.spesimenmuseum.model.Visitor;

public interface IAuthService {
    User login(String username, String password);
    boolean registerEmployee(String username, String password, String email, String phone, String fullName, String position);
    boolean registerSpecialist(String username, String password, String email, String phone, String fullName, String skill, String institution);
    boolean registerVisitor(String username, String password, String email, String phone, String fullName, String residence);
    boolean updateVisitorProfile(Visitor visitor);
}
