package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    public RegisterController(CustomerService customerService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/profile";
        }
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid Customer customer, BindingResult result, Model model) {
        logger.debug("Received registration request for email: {}", customer.getEmail());
        if (result.hasErrors()) {
            logger.warn("Validation errors during registration: {}", result.getAllErrors());
            model.addAttribute("errors", result.getAllErrors());
            return "register";
        }
        try {
            if (customerService.findCustomerByEmail(customer.getEmail()) != null) {
                model.addAttribute("error", "Email already in use. Please choose another.");
                return "register";
            }
            String plainPassword = customer.getPassword();
            customer.setFullName(customer.getFirstName() + " " + customer.getLastName());
            customer.setRole(Customer.Role.USER);
            customer = customerService.createCustomer(customer); // Service handles encoding
            logger.info("Customer created successfully: {}", customer.getEmail());

            Authentication auth = new UsernamePasswordAuthenticationToken(customer.getEmail(), plainPassword);
            auth = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/profile";
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage(), e);
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}