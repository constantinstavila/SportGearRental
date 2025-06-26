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
        if (result.hasErrors()) {
            return "register";
        }
        try {
            customerService.createCustomer(customer);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
    }
}