package com.sportgearrental.app.repository;


import com.sportgearrental.app.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {


    List<Rental> findByCustomerId(Long customerId);
    List<Rental> findByStatus(String status);
}
