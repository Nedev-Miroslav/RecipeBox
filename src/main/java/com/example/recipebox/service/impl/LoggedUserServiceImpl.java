package com.example.recipebox.service.impl;

import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.LoggedUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserServiceImpl implements LoggedUserService {

    private static final String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;


    public LoggedUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public User getUser() {
        return userRepository.findByUsername(getUserDetails().getUsername())
                .orElse(null);
    }

    @Override
    public boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_PREFIX + role));
    }

    @Override
    public UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return !hasRole("ANONYMOUS");
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
