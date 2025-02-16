package com.example.recipebox.web;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.service.FavoritesService;
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

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @PostMapping("/toggle/{id}")
    public String toggleFavorite(@PathVariable Long id) {
        favoritesService.toggleFavorite(id);
        return "redirect:/recipe-details/" + id;
    }

    @GetMapping
    public String showFavorites(Model model) {
        List<Recipe> favoriteRecipes = favoritesService.getFavoriteRecipes();
        model.addAttribute("recipes", favoriteRecipes);
        return "favorites";
    }
}