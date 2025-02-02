package com.example.recipebox.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface RecipeBoxUserDetailsService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException;

}
