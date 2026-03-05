package com.example.bai5.controller;


import com.example.bai5.model.Category;
import com.example.bai5.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "category/list";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }
}