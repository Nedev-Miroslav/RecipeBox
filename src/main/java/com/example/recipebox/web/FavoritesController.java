package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.service.FavoritesService;
import com.example.recipebox.service.LoggedUserService;
import com.example.recipebox.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final LoggedUserService loggedUserService;
    private final RecipeService recipeService;

    public FavoritesController(FavoritesService favoritesService, LoggedUserService loggedUserService, RecipeService recipeService) {
        this.favoritesService = favoritesService;
        this.loggedUserService = loggedUserService;
        this.recipeService = recipeService;
    }

    @PostMapping("/toggle/{id}")
    public String toggleFavorite(@PathVariable Long id) {
        favoritesService.toggleFavorite(id);
        return "redirect:/recipes/" + id;
    }

    @GetMapping
    public String showFavorites(Model model) {
        List<Recipe> favoriteRecipes = favoritesService.getFavoriteRecipes();
        model.addAttribute("recipes", favoriteRecipes);
        return "favorites";
    }
}