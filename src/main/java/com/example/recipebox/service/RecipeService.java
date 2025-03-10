package com.example.recipebox.service;

import com.example.recipebox.model.dto.AddRecipeDTO;
import com.example.recipebox.model.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    boolean createRecipe(AddRecipeDTO data, MultipartFile file) throws IOException;


    Page<Recipe> getPaginatedRecipes(int page, int size);

    Recipe findRecipeById(Long id);

    void deleteRecipe(Long id);

    AddRecipeDTO getRecipeDTOById(Long id);
    boolean updateRecipe(Long id, AddRecipeDTO updatedRecipe, MultipartFile file) throws IOException;
    void addComment(Long recipeId, String content);
    Page<Recipe> searchRecipes(int page, int size, String query);

    boolean favorite(Recipe recipe);

    Page<Recipe> getMyRecipes(int page, int size);

}
