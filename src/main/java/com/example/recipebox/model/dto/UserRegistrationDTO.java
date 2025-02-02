package com.example.recipebox.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 characters!")
    private String username;

    @NotBlank(message = "Please enter email!")
    @Email
    private String email;

    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "Password length must be between 5 and 30 characters!")
    private String password;

    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "Confirm password length must be between 5 and 30 characters!")
    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
