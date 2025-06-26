package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    Equipment createEquipment(Equipment equipment);
    Equipment updateEquipment(Long id, Equipment equipment);
    void deleteEquipment(Long id);
    Equipment findEquipmentById(Long id);
    List<Equipment> findAllEquipments();
    List<Equipment> findAvailableEquipments();
    String getEquipmentNameById(Long id);
}