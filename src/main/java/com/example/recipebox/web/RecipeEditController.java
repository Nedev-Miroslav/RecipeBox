package com.example.recipebox.web;

import com.example.recipebox.model.dto.AddRecipeDTO;
import com.example.recipebox.model.enums.CategoryType;
import com.example.recipebox.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/recipe-edit")
public class RecipeEditController {

    private final RecipeService recipeService;

    public RecipeEditController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/{id}")
    public String editRecipe(@PathVariable Long id, Model model) {
        AddRecipeDTO recipeData = recipeService.getRecipeDTOById(id);
        model.addAttribute("recipeData", recipeData);
        model.addAttribute("recipeId", id);
        model.addAttribute("categoryTypes", CategoryType.values());
        return "recipe-edit";
    }


    @PostMapping("/{id}")
    public String updateRecipe(
            @PathVariable Long id,
            @Valid @ModelAttribute("recipeData") AddRecipeDTO updatedRecipe,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("imageUrl") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeData", updatedRecipe);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeData", bindingResult);
            return "redirect:/recipe-edit/" + id;
        }

        boolean success = recipeService.updateRecipe(id, updatedRecipe, file);

        if (!success) {
            redirectAttributes.addFlashAttribute("recipeError", true);
            return "redirect:/recipe-edit/" + id;
        }

        return "redirect:/recipe-list";
    }
}
