package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.CommentDTO;
import com.example.recipebox.model.entity.Comments;
import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.CommentRepository;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.service.CommentService;
import com.example.recipebox.service.LoggedUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final LoggedUserService loggedUserService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              RecipeRepository recipeRepository,
                              LoggedUserService loggedUserService) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        Recipe recipe = recipeRepository.findById(commentDTO.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        User user = loggedUserService.getUser();

        Comments comment = new Comments();
        comment.setRecipe(recipe);
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());
        comment.setTimestamp(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public List<Comments> getCommentsByRecipe(Long recipeId) {
        return commentRepository.findByRecipeIdOrderByTimestampDesc(recipeId);
    }
}
