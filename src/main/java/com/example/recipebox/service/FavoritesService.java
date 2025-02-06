package com.example.recipebox.service;

import com.example.recipebox.model.entity.Recipe;

import java.util.List;

public interface FavoritesService {
    void toggleFavorite(Long recipeId);
    List<Recipe> getFavoriteRecipes();
}
