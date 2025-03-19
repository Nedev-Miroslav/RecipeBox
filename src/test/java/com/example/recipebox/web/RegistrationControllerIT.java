package com.example.recipebox.web;

import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetRegistrationPage() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("email", "test@example.com")
                        .param("password", "securePass")
                        .param("confirmPassword", "securePass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        Optional<User> userOptional = userRepository.findByUsername("testUser");
        assertThat(userOptional).isPresent();

        User user = userOptional.get();
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(passwordEncoder.matches("securePass", user.getPassword())).isTrue();
    }


    @Test
    public void testRegistrationWithMissingEmail() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("email", "")
                        .param("password", "securePass")
                        .param("confirmPassword", "securePass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerData"))
                .andExpect(flash().attributeExists("registerData"));

        assertThat(userRepository.findByUsername("testUser")).isEmpty();

    }

    @Test
    public void testRegisterWithDuplicateUsername() throws Exception {
        createUser("testUser", "existing@example.com", "securePass");

        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("email", "new@example.com")
                        .param("password", "securePass")
                        .param("confirmPassword", "securePass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attributeExists("hasRegisterError"));

        assertThat(userRepository.findByUsername("testUser")).isPresent();
    }

    @Test
    public void testRegistrationWithMismatchedPasswords() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("email", "test@example.com")
                        .param("password", "securePass")
                        .param("confirmPassword", "wrongPass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attributeExists("missMatchPassword"));

        assertThat(userRepository.findByUsername("testUser")).isEmpty();
    }
    private void createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}