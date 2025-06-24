package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
    CustomerDTO findCustomerById(Long id);
    CustomerDTO findCustomerByEmail(String email);
    List<CustomerDTO> findAllCustomers();
}