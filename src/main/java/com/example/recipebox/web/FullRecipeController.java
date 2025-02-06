package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.service.LoggedUserService;
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
    private final LoggedUserService loggedUserService;

    public FullRecipeController(RecipeService recipeService, LoggedUserService loggedUserService) {
        this.recipeService = recipeService;
        this.loggedUserService = loggedUserService;
    }

    @GetMapping("/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        User user = loggedUserService.getUser();

        boolean isFavorite = user.getFavorites().contains(recipe);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("recipe", recipe);
        return "recipe-details";
    }
}
