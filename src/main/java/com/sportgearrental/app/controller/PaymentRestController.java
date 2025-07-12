package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    private final PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody Rental rental, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.processPayment(rental, amount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.noContent().build();
    }
}