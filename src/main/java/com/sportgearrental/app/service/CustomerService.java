package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
    Customer findCustomerById(Long id);
    Customer findCustomerByEmail(String email);
    Page<Customer> findAllCustomers(Pageable pageable);
    List<Customer> findAllCustomers(); // Overload for non-paginated
}