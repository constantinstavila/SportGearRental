package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalRestController {

    private final RentalService rentalService;

    public RentalRestController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.findRentalsByCustomer(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.findRentalById(id));
    }

    @GetMapping("/customer/{customerId}")
    public List<Rental> getRentalsByCustomer(@PathVariable Long customerId) {
        return rentalService.findRentalsByCustomer(customerId);
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<Rental> getRentalsByEquipment(@PathVariable Long equipmentId) {
        return rentalService.findRentalsByEquipment(equipmentId);
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@Valid @RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.createRental(rental));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @Valid @RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.updateRental(id, rental));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}