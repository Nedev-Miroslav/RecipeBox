package com.example.recipebox.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/recipe-add")
    public String recipeAdd() {
        return "recipe-add";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}