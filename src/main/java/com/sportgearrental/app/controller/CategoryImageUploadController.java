package com.sportgearrental.app.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sportgearrental.app.entity.Category;
import com.sportgearrental.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class CategoryImageUploadController {
    private final CategoryService categoryService;
    private final Cloudinary cloudinary;

    public CategoryImageUploadController(CategoryService categoryService,
                                         @Value("${cloudinary.cloud-name}") String cloudName,
                                         @Value("${cloudinary.api-key}") String apiKey,
                                         @Value("${cloudinary.api-secret}") String apiSecret) {
        this.categoryService = categoryService;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    @GetMapping("/category/{id}/upload-image")
    public String showImageUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findCategoryById(id));
        return "upload-category-image";
    }

    @PostMapping("/category/{id}/upload-image")
    public String uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload");
            model.addAttribute("category", categoryService.findCategoryById(id));
            return "upload-category-image";
        }
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            model.addAttribute("error", "Only image files are allowed");
            model.addAttribute("category", categoryService.findCategoryById(id));
            return "upload-category-image";
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            model.addAttribute("error", "File size exceeds 5MB limit");
            model.addAttribute("category", categoryService.findCategoryById(id));
            return "upload-category-image";
        }
        Category category = categoryService.findCategoryById(id);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        category.setImageUrl((String) uploadResult.get("secure_url"));
        categoryService.updateCategory(id, category);
        return "redirect:/admin/dashboard";
    }
}