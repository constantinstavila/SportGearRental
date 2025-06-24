package com.sportgearrental.app.service;


import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;

import java.math.BigDecimal;

public interface PaymentService {

    Payment processPayment(Rental rental, BigDecimal amount);
    Payment findPaymentById(Long id);
    void cancelPayment(Long id);
}
