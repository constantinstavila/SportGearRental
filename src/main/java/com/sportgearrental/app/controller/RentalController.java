package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.CustomerService;
import com.sportgearrental.app.service.EquipmentService;
import com.sportgearrental.app.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RentalController {

    private final RentalService rentalService;
    private final CustomerService customerService;
    private final EquipmentService equipmentService;

    public RentalController(RentalService rentalService, CustomerService customerService, EquipmentService equipmentService) {
        this.rentalService = rentalService;
        this.customerService = customerService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/rentals")
    public String listRentals(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = customerService.findCustomerByEmail(email);
        List<Rental> rentals = rentalService.findRentalsByCustomer(customer.getId());
        model.addAttribute("rentals", rentals);
        return "rentals";
    }

    @GetMapping("/rentals/new")
    public String showNewRentalForm(Model model, @RequestParam(required = false) Long equipmentId) {
        Rental rental = new Rental();
        if (equipmentId != null) {
            rental.setEquipment(equipmentService.findEquipmentById(equipmentId));
        }
        model.addAttribute("rental", rental);
        model.addAttribute("equipments", equipmentService.findAvailableEquipments());
        return "new-rental";
    }

    @PostMapping("/rentals/new")
    public String createRental(@Valid @ModelAttribute("rental") Rental rental, BindingResult result,
                               Model model, Authentication authentication) {
        if (rental.getStartDate().isAfter(rental.getEndDate())) {
            result.rejectValue("startDate", "error.startDate", "Data de început trebuie să fie înaintea datei de sfârșit.");
        }
        if (rental.getStartDate().isBefore(LocalDate.now())) {
            result.rejectValue("startDate", "error.startDate", "Data de început nu poate fi în trecut.");
        }
        if (result.hasErrors()) {
            model.addAttribute("equipments", equipmentService.findAvailableEquipments());
            return "new-rental";
        }
        String email = authentication.getName();
        Customer customer = customerService.findCustomerByEmail(email);
        rental.setCustomer(customer);
        rental.setStatus("PENDING");
        rental.setEquipmentName(rental.getEquipment().getName());
        rentalService.createRental(rental);
        return "redirect:/rentals";
    }
}