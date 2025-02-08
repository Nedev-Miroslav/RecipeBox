package com.example.recipebox.web;

import com.example.recipebox.model.dto.CommentDTO;
import com.example.recipebox.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute CommentDTO commentDTO, RedirectAttributes redirectAttributes) {
        if (commentDTO.getContent().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Comment cannot be empty!");
            return "redirect:/recipe-details/" + commentDTO.getRecipeId();
        }

        commentService.addComment(commentDTO);
        return "redirect:/recipe-details/" + commentDTO.getRecipeId();
    }
}
