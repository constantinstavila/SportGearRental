package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
    Customer findCustomerById(Long id);
    Customer findCustomerByEmail(String email);
    List<Customer> findAllCustomers();
}