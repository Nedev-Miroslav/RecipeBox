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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class RecipeController {

    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
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

}
