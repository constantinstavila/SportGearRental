package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.repository.EquipmentRepository;
import com.sportgearrental.app.service.EquipmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Long id, Equipment equipment) {
        Equipment existing = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
        existing.setName(equipment.getName());
        existing.setDescription(equipment.getDescription());
        existing.setPricePerDay(equipment.getPricePerDay());
        existing.setAvailable(equipment.isAvailable());
        existing.setEquipmentCondition(equipment.getEquipmentCondition());
        existing.setCategory(equipment.getCategory());
        return equipmentRepository.save(existing);
    }

    @Override
    public void deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipment not found with id: " + id);
        }
        equipmentRepository.deleteById(id);
    }

    @Override
    public Equipment findEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
    }

    @Override
    public List<Equipment> findAllEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<Equipment> findAvailableEquipments() {
        return equipmentRepository.findByAvailableTrue();
    }

    @Override
    public String getEquipmentNameById(Long id) {
        return equipmentRepository.findById(id)
                .map(Equipment::getName)
                .orElse("Unknown");
    }
}