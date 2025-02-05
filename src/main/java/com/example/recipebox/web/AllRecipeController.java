package com.example.recipebox.web;

import com.example.recipebox.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe-list")
public class AllRecipeController {

    private final RecipeService recipeService;


    public AllRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.findAllRecipes());
        return "recipe-list";
    }



}
