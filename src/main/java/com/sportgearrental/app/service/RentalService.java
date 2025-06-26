package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Rental;

import java.util.List;

public interface RentalService {
    Rental createRental(Rental rental);
    Rental updateRental(Long id, Rental rental);
    void deleteRental(Long id);
    Rental findRentalById(Long id);
    List<Rental> findRentalsByCustomer(Long customerId);
    List<Rental> findRentalsByEquipment(Long equipmentId);
}