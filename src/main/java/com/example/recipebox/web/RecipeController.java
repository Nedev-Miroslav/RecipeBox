package com.example.recipebox.web;

import com.example.recipebox.model.dto.AddRecipeDTO;
import com.example.recipebox.model.entity.Comments;
import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.enums.CategoryType;
import com.example.recipebox.service.CommentService;
import com.example.recipebox.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CommentService commentService;

    public RecipeController(RecipeService recipeService, CommentService commentService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
    }

    @ModelAttribute("recipeData")
    public AddRecipeDTO recipeData(){
        return new AddRecipeDTO();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("recipeError");
    }

    @GetMapping("/recipe-add")
    public ModelAndView recipeAdd() {
        ModelAndView modelAndView = new ModelAndView("recipe-add");
        modelAndView.addObject("categoryTypes", CategoryType.values());
        return modelAndView;
    }

    @PostMapping("/recipe-add")
    public String doAddRecipe(
            @Valid AddRecipeDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("imageUrl") MultipartFile file
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeData", bindingResult);

            return "redirect:/recipe-add";
        }

        boolean success = recipeService.createRecipe(data, file);


        if (!success) {
            redirectAttributes.addFlashAttribute("recipeError", true);

            return "redirect:/recipe-add";
        }

        return "redirect:/recipe-list";
    }

    @DeleteMapping("/recipes/{id}")

    public String deleteRecipe(@PathVariable("id") Long id) {

        recipeService.deleteRecipe(id);

        return "redirect:/recipe-list";
    }

    @GetMapping("/recipe-details/{id}")
    public String recipeDetails(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        List<Comments> comments = commentService.getCommentsByRecipe(id);

        boolean isFavorite = recipeService.favorite(recipe);

        model.addAttribute("recipe", recipe);
        model.addAttribute("comments", comments);
        model.addAttribute("isFavorite", isFavorite);

        return "recipe-details";
    }

    @PostMapping("/recipe-details/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content) {
        recipeService.addComment(id, content);
        return "redirect:/recipe-details/" + id;
    }

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("query") String query, Model model) {
        List<Recipe> searchResults = recipeService.searchRecipes(query);
        model.addAttribute("recipes", searchResults);
        return "search-results";
    }

}
