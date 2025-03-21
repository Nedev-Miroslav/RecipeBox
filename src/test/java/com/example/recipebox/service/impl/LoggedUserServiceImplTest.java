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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;



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

    @Test
    public void hasRoleShouldReturnTrueWhenUserHasRole(){
        // Arrange
        GrantedAuthority grantedAuthority = () -> "ROLE_ADMIN";
        doReturn(Collections.singletonList(grantedAuthority)).when(mockAuthentication).getAuthorities();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act
        boolean result = loggedUserServiceToTest.hasRole("ADMIN");

        // Assert
        assertTrue(result);
    }


    @Test
    public void testHasRoleShouldReturnFalseWhenUserDoesNotHaveRole() {
        // Arrange
        when(mockAuthentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act
        boolean result = loggedUserServiceToTest.hasRole("ADMIN");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsAuthenticatedShouldReturnTrueWhenUserIsNotAnonymous() {
        // Arrange
        GrantedAuthority grantedAuthority = () -> "ROLE_USER";
        doReturn(Collections.singletonList(grantedAuthority)).when(mockAuthentication).getAuthorities();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act
        boolean result = loggedUserServiceToTest.isAuthenticated();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAuthenticatedShouldReturnFalseWhenUserIsAnonymous() {
        // Arrange
        GrantedAuthority grantedAuthority = () -> "ROLE_ANONYMOUS";
        doReturn(Collections.singletonList(grantedAuthority)).when(mockAuthentication).getAuthorities();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act
        boolean result = loggedUserServiceToTest.isAuthenticated();

        // Assert
        assertFalse(result);
    }

}
