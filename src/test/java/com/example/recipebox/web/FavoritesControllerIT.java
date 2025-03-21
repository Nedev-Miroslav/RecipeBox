package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FavoritesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private User testUser;
    private Recipe testRecipe;

    @BeforeEach
    void setup() {

        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser = userRepository.save(testUser);


        testRecipe = new Recipe();
        testRecipe.setName("Test Recipe");
        testRecipe.setIngredients("Ingredients");
        testRecipe.setInstructions("Instructions");
        testRecipe.setAuthor(testUser);
        testRecipe = recipeRepository.save(testRecipe);


        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(testUser.getUsername())
                .password(testUser.getPassword())
                .roles("USER")
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @WithMockUser(username = "testUser")
    void testShowFavoritesPage() throws Exception {
        mockMvc.perform(get("/favorites"))
                .andExpect(status().isOk())
                .andExpect(view().name("favorites"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(model().attribute("recipes", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testToggleFavorite() throws Exception {
        mockMvc.perform(post("/favorites/toggle/" + testRecipe.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipe-details/" + testRecipe.getId()));
    }
}
