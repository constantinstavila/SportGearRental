package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentService {
    Equipment createEquipment(Equipment equipment);
    Equipment updateEquipment(Long id, Equipment equipment);
    void deleteEquipment(Long id);
    Equipment findEquipmentById(Long id);
    Page<Equipment> findAllEquipments(Pageable pageable);
    List<Equipment> findAllEquipments(); // Added
    Page<Equipment> findByCategoryId(Long categoryId, Pageable pageable);
    List<Equipment> findAvailableEquipments();
    String getEquipmentNameById(Long id);
    Equipment save(Equipment equipment);
}