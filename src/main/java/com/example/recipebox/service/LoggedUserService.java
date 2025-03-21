package com.example.recipebox.service;

import com.example.recipebox.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoggedUserService {
    User getUser();

    boolean hasRole(String role);

    UserDetails getUserDetails();

    boolean isAuthenticated();

    Authentication getAuthentication();
}
