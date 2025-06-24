package com.sportgearrental.app.controller;

import com.sportgearrental.app.dto.EquipmentDTO;
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
        model.addAttribute("equipments", equipmentService.findAvailableEquipments());
        return "equipments";
    }

    @GetMapping("/equipment/{id}")
    public String equipmentDetails(@PathVariable Long id, Model model) {
        model.addAttribute("equipment", equipmentService.findEquipmentById(id));
        return "equipment-details";
    }
}