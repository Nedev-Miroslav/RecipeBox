package com.example.recipebox.service.impl;
import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.model.enums.RoleType;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.RecipeBoxUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RecipeBoxUserDetailsServiceImpl implements RecipeBoxUserDetailsService {

    private final UserRepository userRepository;

    public RecipeBoxUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::mapToDetails)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private UserDetails mapToDetails(User user) {

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(Role::getRoleType).map(RecipeBoxUserDetailsServiceImpl::mapped).toList())
                .disabled(false)
                .build();
    }


    private static GrantedAuthority mapped(RoleType role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}