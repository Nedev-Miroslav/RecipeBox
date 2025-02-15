package com.example.recipebox.model.dto;

import com.example.recipebox.model.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddRecipeDTO {

    @NotBlank(message = "{message.name}")
    private String name;

    @NotBlank(message = "{message.ingredients}")
    private String ingredients;

    @NotBlank(message = "{message.instructions}")
    private String instructions;

    @NotNull(message = "{message.category}")
    private CategoryType categoryType;

    public AddRecipeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
