package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.dto.EquipmentDTO;
import com.sportgearrental.app.entity.Category;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.mapper.EntityMapper;
import com.sportgearrental.app.repository.CategoryRepository;
import com.sportgearrental.app.repository.EquipmentRepository;
import com.sportgearrental.app.service.EquipmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;
    private final EntityMapper entityMapper;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, CategoryRepository categoryRepository, EntityMapper entityMapper) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public EquipmentDTO createEquipment(EquipmentDTO equipmentDTO) {
        Category category = categoryRepository.findById(equipmentDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + equipmentDTO.getCategoryId()));
        Equipment equipment = entityMapper.toEquipmentEntity(equipmentDTO);
        equipment.setCategory(category);
        equipment = equipmentRepository.save(equipment);
        return entityMapper.toEquipmentDTO(equipment);
    }

    @Override
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment existing = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
        Category category = categoryRepository.findById(equipmentDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + equipmentDTO.getCategoryId()));
        existing.setName(equipmentDTO.getName());
        existing.setDescription(equipmentDTO.getDescription());
        existing.setEquipmentCondition(equipmentDTO.getEquipmentCondition());
        existing.setPricePerDay(equipmentDTO.getPricePerDay());
        existing.setAvailable(equipmentDTO.isAvailable());
        existing.setCategory(category);
        equipmentRepository.save(existing);
        return entityMapper.toEquipmentDTO(existing);
    }

    @Override
    public void deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipment not found with id: " + id);
        }
        equipmentRepository.deleteById(id);
    }

    @Override
    public EquipmentDTO findEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + id));
        return entityMapper.toEquipmentDTO(equipment);
    }

    @Override
    public List<EquipmentDTO> findAllEquipments() {
        return equipmentRepository.findAll().stream()
                .map(entityMapper::toEquipmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentDTO> findAvailableEquipments() {
        return equipmentRepository.findByAvailableTrue().stream()
                .map(entityMapper::toEquipmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentDTO> findEquipmentsByCategory(Long categoryId) {
        return equipmentRepository.findByCategoryId(categoryId).stream()
                .map(entityMapper::toEquipmentDTO)
                .collect(Collectors.toList());
    }
}