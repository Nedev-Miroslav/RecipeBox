package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.AddRecipeDTO;
import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.LoggedUserService;
import com.example.recipebox.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final LoggedUserService loggedUserService;



    public RecipeServiceImpl(RecipeRepository repository, UserRepository userRepository, ModelMapper modelMapper, LoggedUserService loggedUserService) {
        this.recipeRepository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.loggedUserService = loggedUserService;
    }


    @Override
    public boolean createRecipe(AddRecipeDTO data, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return false;
        }


        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        } else {
            return false;
        }


        String uniqueFilename = UUID.randomUUID().toString() + extension;


        Path uploadDirectory = Paths.get("src", "main", "resources", "uploads").normalize();
        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        Path destinationFile = uploadDirectory.resolve(uniqueFilename);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        User user = loggedUserService.getUser();


        Recipe toInsert = modelMapper.map(data, Recipe.class);
        toInsert.setAuthor(user);
        toInsert.setImageUrl(uniqueFilename);

        recipeRepository.save(toInsert);
        return true;
    }


    @Override
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        User currentUser = loggedUserService.getUser();

        if (!recipe.getAuthor().equals(currentUser)) {
            throw new SecurityException("You are not the author of this recipe!");
        }

        List<User> usersWithFavorite = userRepository.findAllByFavoritesContaining(recipe);

        for (User user : usersWithFavorite) {
            Recipe managedRecipe = recipeRepository.findById(recipeId).orElseThrow();
            user.getFavorites().remove(managedRecipe);
            userRepository.save(user);
        }



        if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
            try {
                Path uploadDirectory = Paths.get("src", "main", "resources", "uploads").normalize();
                Path imagePath = uploadDirectory.resolve(recipe.getImageUrl());

                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file: " + e.getMessage());
            }
        }

        recipeRepository.delete(recipe);
    }
}
