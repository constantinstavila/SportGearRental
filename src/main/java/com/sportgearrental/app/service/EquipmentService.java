package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.EquipmentDTO;
import java.util.List;

public interface EquipmentService {
    EquipmentDTO createEquipment(EquipmentDTO equipmentDTO);
    EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO);
    void deleteEquipment(Long id);
    EquipmentDTO findEquipmentById(Long id);
    List<EquipmentDTO> findAllEquipments();
    List<EquipmentDTO> findAvailableEquipments();
    List<EquipmentDTO> findEquipmentsByCategory(Long categoryId);
}