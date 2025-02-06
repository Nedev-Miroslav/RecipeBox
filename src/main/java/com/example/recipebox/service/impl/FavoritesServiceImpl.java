package com.example.recipebox.service.impl;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.FavoritesService;
import com.example.recipebox.service.LoggedUserService;
import com.example.recipebox.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService{

    private final UserRepository userRepository;
    private final RecipeService recipeService;
    private final LoggedUserService loggedUserService;

    public FavoritesServiceImpl(UserRepository userRepository, RecipeService recipeService, LoggedUserService loggedUserService) {
        this.userRepository = userRepository;
        this.recipeService = recipeService;
        this.loggedUserService = loggedUserService;
    }

    @Transactional
    public void toggleFavorite(Long recipeId) {
        User user = loggedUserService.getUser();
        Recipe recipe = recipeService.findRecipeById(recipeId);

        if (user.getFavorites().contains(recipe)) {
            user.getFavorites().remove(recipe);  // Премахване от любими
        } else {
            user.getFavorites().add(recipe);  // Добавяне в любими
        }

        userRepository.save(user);
    }

    public List<Recipe> getFavoriteRecipes() {
        return loggedUserService.getUser().getFavorites();
    }
}