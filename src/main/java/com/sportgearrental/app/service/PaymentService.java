package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.PaymentDTO;
import com.sportgearrental.app.dto.RentalDTO;
import java.math.BigDecimal;

public interface PaymentService {
    PaymentDTO processPayment(RentalDTO rental, BigDecimal amount);
    PaymentDTO findPaymentById(Long id);
    void cancelPayment(Long id);
}