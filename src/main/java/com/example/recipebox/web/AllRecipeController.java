package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recipe-list")
public class AllRecipeController {

    private final RecipeService recipeService;


    public AllRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getAllRecipes(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size) {
        Page<Recipe> recipePage = recipeService.getPaginatedRecipes(page, size);

        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe-list";
    }

}
