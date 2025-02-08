package com.example.recipebox.service;

import com.example.recipebox.model.dto.CommentDTO;
import com.example.recipebox.model.entity.Comments;

import java.util.List;

public interface CommentService {
    void addComment(CommentDTO commentDTO);
    List<Comments> getCommentsByRecipe(Long recipeId);
}
