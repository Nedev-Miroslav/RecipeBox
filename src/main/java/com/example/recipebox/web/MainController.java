package com.example.recipebox.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin("*")
@Controller
public class MainController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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