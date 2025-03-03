package com.example.recipebox.web;

import com.example.recipebox.model.dto.UserRegistrationDTO;
import com.example.recipebox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;


    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();

    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("hasRegisterError");
    }

    @ModelAttribute
    public void addAttributeMissMatchPassword(Model model) {
        model.addAttribute("missMatchPassword");
    }

    @GetMapping("/register")
    public String register() {
        return "register";

    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegistrationDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes

    ){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";

        }

        if(!data.getPassword().equals(data.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("missMatchPassword", true);
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";
        }
        boolean success = userService.registerUser(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("hasRegisterError", true);
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }



}
