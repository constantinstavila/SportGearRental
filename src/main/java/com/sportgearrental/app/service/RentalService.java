package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.RentalDTO;
import java.util.List;

public interface RentalService {
    RentalDTO createRental(RentalDTO rentalDTO);
    RentalDTO updateRental(Long id, RentalDTO rentalDTO);
    void deleteRental(Long id);
    RentalDTO findRentalById(Long id);
    List<RentalDTO> findRentalsByCustomer(Long customerId);
    List<RentalDTO> findRentalsByEquipment(Long equipmentId);
}