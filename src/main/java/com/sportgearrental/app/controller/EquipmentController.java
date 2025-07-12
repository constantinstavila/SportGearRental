package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/equipments")
    public String listEquipments(Model model) {
        model.addAttribute("equipments", equipmentService.findAllEquipments());
        return "equipments";
    }

    @GetMapping("/equipment/{id}")
    public String showEquipmentDetail(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.findEquipmentById(id);
        model.addAttribute("equipment", equipment);
        return "equipment-detail";
    }
}