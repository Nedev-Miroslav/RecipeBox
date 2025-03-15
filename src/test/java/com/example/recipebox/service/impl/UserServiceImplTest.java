package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.UserRegistrationDTO;
import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.model.enums.RoleType;
import com.example.recipebox.repository.RoleRepository;
import com.example.recipebox.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl userServiceToTest;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp() {
        userServiceToTest = new UserServiceImpl(mockUserRepository, mockRoleRepository, modelMapper, mockPasswordEncoder);
    }


    @Test
    public void testUserRegistrationReturnTrueWhenUserIsNew(){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("newUser");
        userRegistrationDTO.setEmail("testEmail@gmail.com");
        userRegistrationDTO.setPassword("testPassword");
        userRegistrationDTO.setConfirmPassword("testPassword");


        when(mockUserRepository.findByUsername(userRegistrationDTO.getUsername())).thenReturn(Optional.empty());

        Role userRole = new Role();
        when(mockRoleRepository.findByRoleType(RoleType.USER)).thenReturn(userRole);

        when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword())).thenReturn("encodedPassword");

        boolean result = userServiceToTest.registerUser(userRegistrationDTO);

        Assertions.assertTrue(result);
        verify(mockUserRepository).save(userCaptor.capture());

        User actualSavedEntityUser = userCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntityUser);
        Assertions.assertEquals("newUser", actualSavedEntityUser.getUsername());
        Assertions.assertEquals("testEmail@gmail.com", actualSavedEntityUser.getEmail());
        Assertions.assertEquals("encodedPassword", actualSavedEntityUser.getPassword());
        Assertions.assertTrue(actualSavedEntityUser.getRoles().contains(userRole));

    }

    @Test
    public void testUserShouldReturnFalseWhenUserAlreadyExists(){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("existingUser");

        when(mockUserRepository.findByUsername(userRegistrationDTO.getUsername())).thenReturn(Optional.of(new User()));

        boolean result = userServiceToTest.registerUser(userRegistrationDTO);

        Assertions.assertFalse(result);
        verify(mockUserRepository, never()).save(any(User.class));

    }


}
