package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
public class FullRecipeController {

    private final RecipeService recipeService;


    public FullRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipe-details";
    }
}
