package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}