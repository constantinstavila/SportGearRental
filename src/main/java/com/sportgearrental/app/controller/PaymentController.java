package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final String stripePublicKey;

    public PaymentController(PaymentService paymentService,
                             @Value("${stripe.secret-key}") String stripeSecretKey,
                             @Value("${stripe.public-key}") String stripePublicKey) {
        this.paymentService = paymentService;
        this.stripePublicKey = stripePublicKey;
        Stripe.apiKey = stripeSecretKey;
    }

    @GetMapping("/rentals/{id}/payment")
    public String showPaymentForm(@PathVariable Long id, Model model) {
        try {
            Rental rental = paymentService.findRentalById(id);
            if (rental.getPayment() != null && "COMPLETED".equals(rental.getPayment().getStatus())) {
                model.addAttribute("error", "payment.already.completed");
                return "error";
            }
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (rental.getTotalCost().doubleValue() * 100))
                    .setCurrency("RON")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods
                                    .builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            model.addAttribute("rental", rental);
            model.addAttribute("stripePublicKey", stripePublicKey);
            model.addAttribute("clientSecret", paymentIntent.getClientSecret());
            return "payment";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "rental.not.found");
            return "error";
        } catch (StripeException e) {
            model.addAttribute("error", "payment.stripe.error");
            return "error";
        }
    }

    @PostMapping("/rentals/{id}/payment")
    public String processPayment(@PathVariable Long id, Model model, @RequestParam("stripePaymentId") String stripePaymentId) {
        try {
            Rental rental = paymentService.findRentalById(id);
            if (rental.getPayment() != null && "COMPLETED".equals(rental.getPayment().getStatus())) {
                model.addAttribute("error", "payment.already.completed");
                return "error";
            }
            paymentService.processPayment(rental, rental.getTotalCost(), stripePaymentId);
            return "redirect:/rentals?payment=success";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "rental.not.found");
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "payment.error");
            return "redirect:/rentals/" + id + "/payment?error=true";
        }
    }

    @PostMapping("/rentals/{id}/cancel-payment")
    public String cancelPayment(@PathVariable Long id, Model model) {
        try {
            Rental rental = paymentService.findRentalById(id);
            if (rental.getPayment() == null || !"COMPLETED".equals(rental.getPayment().getStatus())) {
                model.addAttribute("error", "payment.not.completed");
                return "error";
            }
            paymentService.cancelPayment(rental.getPayment().getId());
            return "redirect:/rentals?payment=cancelled";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "rental.not.found");
            return "error";
        } catch (RuntimeException e) {
            model.addAttribute("error", "payment.stripe.error");
            return "error";
        }
    }
}