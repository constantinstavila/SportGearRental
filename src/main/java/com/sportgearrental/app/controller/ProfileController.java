package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.CustomerService;
import com.sportgearrental.app.service.RentalService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final CustomerService customerService;
    private final RentalService rentalService;

    public ProfileController(CustomerService customerService, RentalService rentalService) {
        this.customerService = customerService;
        this.rentalService = rentalService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = customerService.findCustomerByEmail(email);
        List<Rental> rentals = rentalService.findRentalsByCustomer(customer.getId());
        model.addAttribute("customer", customer);
        model.addAttribute("rentals", rentals);
        return "profile";
    }
}