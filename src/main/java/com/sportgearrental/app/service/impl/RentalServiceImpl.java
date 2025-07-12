package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.repository.CustomerRepository;
import com.sportgearrental.app.repository.EquipmentRepository;
import com.sportgearrental.app.repository.RentalRepository;
import com.sportgearrental.app.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final EquipmentRepository equipmentRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, CustomerRepository customerRepository,
                             EquipmentRepository equipmentRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Rental createRental(Rental rental) {
        Customer customer = customerRepository.findById(rental.getCustomer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + rental.getCustomer().getId()));
        Equipment equipment = equipmentRepository.findById(rental.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + rental.getEquipment().getId()));
        if (!equipment.isAvailable()) {
            throw new IllegalStateException("Equipment is not available for rental");
        }
        if (rental.getStartDate().isAfter(rental.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        rental.setCustomer(customer);
        rental.setEquipment(equipment);
        rental.setEquipmentName(equipment.getName());
        equipment.setAvailable(false);
        rentalRepository.save(rental);
        equipmentRepository.save(equipment);
        return rental;
    }

    @Override
    public Rental updateRental(Long id, Rental rental) {
        Rental existing = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        Customer customer = customerRepository.findById(rental.getCustomer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + rental.getCustomer().getId()));
        Equipment equipment = equipmentRepository.findById(rental.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + rental.getEquipment().getId()));
        if (rental.getStartDate().isAfter(rental.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        existing.setStartDate(rental.getStartDate());
        existing.setEndDate(rental.getEndDate());
        existing.setTotalCost(rental.getTotalCost());
        existing.setStatus(rental.getStatus());
        existing.setCustomer(customer);
        existing.setEquipment(equipment);
        existing.setEquipmentName(equipment.getName());
        return rentalRepository.save(existing);
    }

    @Override
    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        Equipment equipment = rental.getEquipment();
        equipment.setAvailable(true);
        equipmentRepository.save(equipment);
        rentalRepository.deleteById(id);
    }

    @Override
    public Rental findRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        rental.setEquipmentName(rental.getEquipment().getName());
        return rental;
    }

    @Override
    public List<Rental> findRentalsByCustomer(Long customerId) {
        List<Rental> rentals = rentalRepository.findByCustomerId(customerId);
        rentals.forEach(rental -> rental.setEquipmentName(rental.getEquipment().getName()));
        return rentals;
    }

    @Override
    public List<Rental> findRentalsByEquipment(Long equipmentId) {
        List<Rental> rentals = rentalRepository.findByEquipmentId(equipmentId);
        rentals.forEach(rental -> rental.setEquipmentName(rental.getEquipment().getName()));
        return rentals;
    }
}