package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.repository.PaymentRepository;
import com.sportgearrental.app.repository.RentalRepository;
import com.sportgearrental.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RentalRepository rentalRepository) {
        this.paymentRepository = paymentRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Payment processPayment(Rental rental, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setMethod("CARD");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("COMPLETED");
        payment.setRental(rental);
        rental.setPayment(payment);
        paymentRepository.save(payment);
        rentalRepository.save(rental);
        return payment;
    }

    @Override
    public Payment processPayment(Rental rental, BigDecimal amount, String stripePaymentId) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setMethod("CARD");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("COMPLETED");
        payment.setStripePaymentId(stripePaymentId);
        payment.setRental(rental);
        rental.setPayment(payment);
        paymentRepository.save(payment);
        rentalRepository.save(rental);
        return payment;
    }

    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public Rental findRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
    }

    @Override
    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(payment.getStripePaymentId())
                    .build();
            Refund refund = Refund.create(params);
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to refund payment: " + e.getMessage(), e);
        }
    }
}