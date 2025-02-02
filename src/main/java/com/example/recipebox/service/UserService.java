package com.example.recipebox.service;

import com.example.recipebox.model.dto.UserRegistrationDTO;

public interface UserService {
    boolean registerUser(UserRegistrationDTO data);
}
