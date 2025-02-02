package com.example.recipebox.service.impl;

import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
