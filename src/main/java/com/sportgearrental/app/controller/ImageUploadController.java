package com.sportgearrental.app.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.service.EquipmentService;
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
public class ImageUploadController {

    private final EquipmentService equipmentService;
    private final Cloudinary cloudinary;

    public ImageUploadController(EquipmentService equipmentService,
                                 @Value("${cloudinary.cloud-name}") String cloudName,
                                 @Value("${cloudinary.api-key}") String apiKey,
                                 @Value("${cloudinary.api-secret}") String apiSecret) {
        this.equipmentService = equipmentService;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    @GetMapping("/equipment/{id}/upload-image")
    public String showImageUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("equipment", equipmentService.findEquipmentById(id));
        return "upload-image";
    }

    @PostMapping("/equipment/{id}/upload-image")
    public String uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload");
            model.addAttribute("equipment", equipmentService.findEquipmentById(id));
            return "upload-image";
        }
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            model.addAttribute("error", "Only image files are allowed");
            model.addAttribute("equipment", equipmentService.findEquipmentById(id));
            return "upload-image";
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            model.addAttribute("error", "File size exceeds 5MB limit");
            model.addAttribute("equipment", equipmentService.findEquipmentById(id));
            return "upload-image";
        }
        Equipment equipment = equipmentService.findEquipmentById(id);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        equipment.setImageUrl((String) uploadResult.get("secure_url"));
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/equipment/" + id;
    }
}