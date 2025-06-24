package com.sportgearrental.app.service;


import com.sportgearrental.app.entity.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EquipmentService {

                               //CRUD operations

    //  CREATE
    Equipment createEquipment (Equipment equipment);

    //  UPDATE
    Equipment updateEquipment (Long id, Equipment equipment);

    //  DELETE
    void deleteEquipment (Long id);

    //   Find equipment by ID
    Equipment findEquipmentById (Long id);

    //Find all equipments
    List<Equipment> findAllEquipments();

    //  Find all available equipments
    List<Equipment> findAvailableEquipments();

    //  Find by equipments by categories
    List<Equipment> findEquipmentsByCategory (Long categoryID);
}
