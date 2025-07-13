package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.repository.EquipmentRepository;
import com.sportgearrental.app.repository.CustomerRepository;
import com.sportgearrental.app.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RentalController {

    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final RentalService rentalService;

    public RentalController(EquipmentRepository equipmentRepository, CustomerRepository customerRepository, RentalService rentalService) {
        this.equipmentRepository = equipmentRepository;
        this.customerRepository = customerRepository;
        this.rentalService = rentalService;
    }

    @GetMapping("/rentals/new")
    public String showNewRentalForm(Model model, Authentication authentication) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("equipments", equipmentRepository.findByAvailableTrue());
        model.addAttribute("customers", customerRepository.findAll());
        return "new-rental";
    }

    @PostMapping("/rentals/new")
    public String createRental(@Valid Rental rental, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("equipments", equipmentRepository.findByAvailableTrue());
            model.addAttribute("customers", customerRepository.findAll());
            return "new-rental";
        }
        try {
            Customer customer = customerRepository.findByEmail(authentication.getName());
            rental.setCustomer(customer);
            rentalService.createRental(rental);
            return "redirect:/rentals";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("equipments", equipmentRepository.findByAvailableTrue());
            model.addAttribute("customers", customerRepository.findAll());
            return "new-rental";
        }
    }

    @GetMapping("/rentals")
    public String listRentals(Model model, Authentication authentication) {
        Customer customer = customerRepository.findByEmail(authentication.getName());
        model.addAttribute("rentals", rentalService.findRentalsByCustomer(customer.getId()));
        return "rentals";
    }
}