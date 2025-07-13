package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/rental-confirmation")
    public ResponseEntity<Void> sendRentalConfirmation(@RequestBody Rental rental) {
        Customer customer = rental.getCustomer();
        notificationService.sendRentalConfirmation(customer, rental);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment-confirmation")
    public ResponseEntity<Void> sendPaymentConfirmation(@RequestBody Payment payment) {
        Customer customer = payment.getRental().getCustomer();
        notificationService.sendPaymentConfirmation(customer, payment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rental-reminder")
    public ResponseEntity<Void> sendRentalReminder(@RequestBody Rental rental) {
        Customer customer = rental.getCustomer();
        notificationService.sendRentalReminder(customer, rental);
        return ResponseEntity.ok().build();
    }
}