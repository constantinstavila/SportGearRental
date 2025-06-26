package com.sportgearrental.app.controller;

import com.sportgearrental.app.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        return "index";
    }
}