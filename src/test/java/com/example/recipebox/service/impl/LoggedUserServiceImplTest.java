package com.example.recipebox.service.impl;

import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;;

@ExtendWith(MockitoExtension.class)
public class LoggedUserServiceImplTest {

    @InjectMocks
    private LoggedUserServiceImpl loggedUserServiceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private SecurityContext mockSecurityContext;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    public void setUp() {
        loggedUserServiceToTest = new LoggedUserServiceImpl(mockUserRepository);
    }


    @Test
    public void testGetUserReturnUserWhenUserExist(){
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");

        when(mockUserDetails.getUsername()).thenReturn("testUser");
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockUserRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // Act
        User result = loggedUserServiceToTest.getUser();

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());

    }

    @Test
    public void testGetUserShouldReturnNullWhenUserDoesntExist(){
        // Arrange
        when(mockUserDetails.getUsername()).thenReturn("unknownUser");
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockUserRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act
        User result =loggedUserServiceToTest.getUser();

        // Assert
        assertNull(result);


    }




}
