package com.sportgearrental.app.controller;

import com.sportgearrental.app.dto.CustomerDTO;
import com.sportgearrental.app.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("customer", new CustomerDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(CustomerDTO customerDTO) {
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customerService.createCustomer(customerDTO);
        return "redirect:/login";
    }
}