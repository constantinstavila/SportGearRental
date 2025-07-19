package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.repository.PaymentRepository;
import com.sportgearrental.app.repository.RentalRepository;
import com.sportgearrental.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
        if (rental.getPayment() != null && "COMPLETED".equals(rental.getPayment().getStatus())) {
            throw new IllegalStateException("Payment already completed");
        }
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
        if (rental.getPayment() != null && "COMPLETED".equals(rental.getPayment().getStatus())) {
            throw new IllegalStateException("Payment already completed");
        }
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(stripePaymentId);
            if (!"succeeded".equals(paymentIntent.getStatus())) {
                throw new RuntimeException("Payment not confirmed by Stripe");
            }
        } catch (StripeException e) {
            throw new RuntimeException("Failed to verify payment: " + e.getMessage(), e);
        }
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
        if (!"COMPLETED".equals(payment.getStatus())) {
            throw new IllegalStateException("Payment not completed");
        }
        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(payment.getStripePaymentId())
                    .build();
            Refund refund = Refund.create(params);
            if (!"succeeded".equals(refund.getStatus())) {
                throw new RuntimeException("Refund failed: " + refund.getFailureReason());
            }
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to refund payment: " + e.getMessage(), e);
        }
    }
}