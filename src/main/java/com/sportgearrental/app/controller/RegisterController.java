package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

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
        if (result.hasErrors()) {
            logger.warn("Validation errors during registration for email: {}", customer.getEmail());
            return "register";
        }
        try {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerService.createCustomer(customer);
            logger.info("Customer created successfully: {}", customer.getEmail());
            return "redirect:/login?success=true";
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage());
            model.addAttribute("error", "Registration failed. Email may already exist.");
            return "register";
        }
    }
}