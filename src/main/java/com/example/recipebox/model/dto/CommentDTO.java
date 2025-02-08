package com.example.recipebox.model.dto;

public class CommentDTO {

    private Long recipeId;

    private String content;

    public CommentDTO() {
    }

    public CommentDTO(Long recipeId, String content) {
        this.recipeId = recipeId;
        this.content = content;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
