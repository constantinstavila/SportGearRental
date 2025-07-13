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

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
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

        // Check equipment availability
        if (!equipment.isAvailable()) {
            throw new IllegalStateException("Equipment is not available for rental");
        }

        // Check for overlapping rentals
        List<Rental> existingRentals = rentalRepository.findByEquipmentId(equipment.getId());
        for (Rental existing : existingRentals) {
            if (!(rental.getEndDate().isBefore(existing.getStartDate()) || rental.getStartDate().isAfter(existing.getEndDate()))) {
                throw new IllegalStateException("Equipment is already rented for the selected dates");
            }
        }

        if (rental.getStartDate().isAfter(rental.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        // Calculate total cost
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate()) + 1;
        BigDecimal totalCost = equipment.getPricePerDay().multiply(BigDecimal.valueOf(days));
        rental.setTotalCost(totalCost);

        rental.setCustomer(customer);
        rental.setEquipment(equipment);
        rental.setStatus("PENDING");
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

        // Update total cost
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate()) + 1;
        BigDecimal totalCost = equipment.getPricePerDay().multiply(BigDecimal.valueOf(days));

        existing.setStartDate(rental.getStartDate());
        existing.setEndDate(rental.getEndDate());
        existing.setTotalCost(totalCost);
        existing.setStatus(rental.getStatus());
        existing.setCustomer(customer);
        existing.setEquipment(equipment);
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
        return rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
    }

    @Override
    public List<Rental> findRentalsByCustomer(Long customerId) {
        return customerId == null ? rentalRepository.findAll() : rentalRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Rental> findRentalsByEquipment(Long equipmentId) {
        return rentalRepository.findByEquipmentId(equipmentId);
    }

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
}