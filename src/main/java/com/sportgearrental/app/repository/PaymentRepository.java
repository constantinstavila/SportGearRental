package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
