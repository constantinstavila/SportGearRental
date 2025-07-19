package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.service.CategoryService;
import com.sportgearrental.app.service.EquipmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listEquipments(@RequestParam(value = "category", required = false) Long categoryId,
                                 @PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Equipment> equipmentsPage;
        if (categoryId != null) {
            equipmentsPage = equipmentService.findByCategoryId(categoryId, pageable);
        } else {
            equipmentsPage = equipmentService.findAllEquipments(pageable);
        }
        model.addAttribute("equipments", equipmentsPage.getContent());
        model.addAttribute("page", equipmentsPage);
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