package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid Customer customer, BindingResult result, Model model) {
        System.out.println("Received POST /register for email: " + customer.getEmail());
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            return "register";
        }
        try {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerService.createCustomer(customer);
            System.out.println("Customer created successfully: " + customer.getEmail());
            return "redirect:/login?success=true";
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
            model.addAttribute("error", "Email already exists or registration failed");
            return "register";
        }
    }
}