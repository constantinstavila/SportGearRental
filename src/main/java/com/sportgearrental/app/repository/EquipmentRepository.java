package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByAvailableTrue();
}