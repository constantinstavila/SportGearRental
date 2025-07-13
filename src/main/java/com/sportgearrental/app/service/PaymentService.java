package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;

import java.math.BigDecimal;

public interface PaymentService {
    Payment processPayment(Rental rental, BigDecimal amount);
    Payment processPayment(Rental rental, BigDecimal amount, String stripePaymentId);
    Payment findPaymentById(Long id);
    Rental findRentalById(Long id);
    void cancelPayment(Long id);
}