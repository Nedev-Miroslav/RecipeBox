package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.UserRegistrationDTO;
import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.model.enums.RoleType;
import com.example.recipebox.repository.RoleRepository;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean registerUser(UserRegistrationDTO data) {
        Optional<User> existingUser = userRepository.findByUsername(data.getUsername());
        if(existingUser.isPresent()) {
            return false;
        }

        User mappedEntity = modelMapper.map(data, User.class);
        Role role = repository.findByRoleType(RoleType.USER);

        mappedEntity.setPassword(passwordEncoder.encode(data.getPassword()));
        mappedEntity.getRoles().add(role);

        userRepository.save(mappedEntity);
        return true;
    }
}
