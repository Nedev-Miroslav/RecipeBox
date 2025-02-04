package com.example.recipebox.service;

import com.example.recipebox.model.dto.AddRecipeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RecipeService {
    boolean createRecipe(AddRecipeDTO data, MultipartFile file) throws IOException;
}
