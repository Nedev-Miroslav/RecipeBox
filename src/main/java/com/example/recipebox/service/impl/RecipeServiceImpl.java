package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.AddRecipeDTO;
import com.example.recipebox.model.entity.Comments;
import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.CommentRepository;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.repository.UserRepository;
import com.example.recipebox.service.LoggedUserService;
import com.example.recipebox.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final LoggedUserService loggedUserService;


    public RecipeServiceImpl(RecipeRepository repository, CommentRepository commentRepository, UserRepository userRepository, ModelMapper modelMapper, LoggedUserService loggedUserService) {
        this.recipeRepository = repository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public Page<Recipe> getPaginatedRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeRepository.findAll(pageable);
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
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        User currentUser = loggedUserService.getUser();


        commentRepository.deleteByRecipe(recipe);

        List<User> usersWithFavorite = userRepository.findAllByFavoritesContaining(recipe);
        for (User user : usersWithFavorite) {
            user.getFavorites().remove(recipe);
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

    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        } else {
            throw new IOException("Invalid file name");
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

        return uniqueFilename;
    }

    @Override
    public AddRecipeDTO getRecipeDTOById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        return modelMapper.map(recipe, AddRecipeDTO.class);
    }

    private void deleteImage(String filename) {
        try {
            Path uploadDirectory = Paths.get("src", "main", "resources", "uploads").normalize();
            Path imagePath = uploadDirectory.resolve(filename);

            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete old image: " + filename, e);
        }
    }

    @Override
    @Transactional
    public boolean updateRecipe(Long id, AddRecipeDTO updatedRecipe, MultipartFile file) throws IOException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));


        recipe.setName(updatedRecipe.getName());
        recipe.setIngredients(updatedRecipe.getIngredients());
        recipe.setInstructions(updatedRecipe.getInstructions());

        if (file != null && !file.isEmpty()) {

            String oldImage = recipe.getImageUrl();

            String newFilename = saveImage(file);
            recipe.setImageUrl(newFilename);

            if (oldImage != null && !oldImage.isEmpty()) {
                deleteImage(oldImage);
            }
        }

        recipeRepository.save(recipe);
        return true;
    }

    @Override
    public void addComment(Long recipeId, String content) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        User user = loggedUserService.getUser();

        Comments comment = new Comments();
        comment.setRecipe(recipe);
        comment.setUser(user);
        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public List<Recipe> searchRecipes(String query) {
        return recipeRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    public boolean favorite(Recipe recipe) {
        User user = loggedUserService.getUser();
        for (Recipe favorite : user.getFavorites()) {
            if (favorite.getId() == recipe.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<Recipe> getMyRecipes(int page, int size) {
        User user = loggedUserService.getUser();
        Pageable pageable = PageRequest.of(page, size);
        return recipeRepository.findAllByAuthorId(pageable, user.getId());
    }
}