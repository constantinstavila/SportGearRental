package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.repository.CustomerRepository;
import com.sportgearrental.app.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword())); // Encoding here only
        if (customer.getRole() == null) {
            customer.setRole(Customer.Role.USER);
        }
        logger.info("Creating customer: {}", customer.getEmail());
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setAddress(customer.getAddress());
        existing.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getRole() != null) {
            existing.setRole(customer.getRole());
        }
        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}