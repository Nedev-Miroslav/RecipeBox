package com.example.recipebox.web;

import com.example.recipebox.model.dto.UserLoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginData"));
    }

    @Test
    public void testGetLoginErrorPage() throws Exception {
        mockMvc.perform(get("/users/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("showError"))
                .andExpect(model().attribute("showError", true))
                .andExpect(model().attributeExists("loginData"))
                .andExpect(model().attribute("loginData", org.hamcrest.Matchers.instanceOf(UserLoginDTO.class)));
    }
}
