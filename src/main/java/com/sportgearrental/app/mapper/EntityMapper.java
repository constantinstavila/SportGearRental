package com.sportgearrental.app.mapper;

import com.sportgearrental.app.dto.CustomerDTO;
import com.sportgearrental.app.dto.EquipmentDTO;
import com.sportgearrental.app.dto.RentalDTO;
import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    CustomerDTO toCustomerDTO(Customer customer);

    @Mapping(target = "password", ignore = true)
    Customer toCustomerEntity(CustomerDTO customerDTO);

    @Mapping(source = "category.id", target = "categoryId")
    EquipmentDTO toEquipmentDTO(Equipment equipment);

    @Mapping(source = "categoryId", target = "category.id")
    Equipment toEquipmentEntity(EquipmentDTO equipmentDTO);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "equipment.id", target = "equipmentId")
    RentalDTO toRentalDTO(Rental rental);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "equipmentId", target = "equipment.id")
    Rental toRentalEntity(RentalDTO rentalDTO);
}