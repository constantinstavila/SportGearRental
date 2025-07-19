package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.service.CategoryService;
import com.sportgearrental.app.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final CategoryService categoryService;

    public EquipmentController(EquipmentService equipmentService,
                               CategoryService categoryService) {
        this.equipmentService = equipmentService;
        this.categoryService = categoryService;
    }

    @GetMapping("/equipments")
    public String listEquipments(@RequestParam(value = "category", required = false) Long categoryId, Model model) {
        List<Equipment> equipments = categoryId != null
                ? equipmentService.findAllEquipments().stream()
                .filter(e -> e.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList())
                : equipmentService.findAllEquipments();
        model.addAttribute("equipments", equipments);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "equipments";
    }

    @GetMapping("/equipment/{id}")
    public String showEquipmentDetail(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.findEquipmentById(id);
        model.addAttribute("equipment", equipment);
        return "equipment-detail";
    }

}
