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
    public ResponseEntity<Void> sendRentalConfirmation(@RequestBody Customer customer, @RequestBody Rental rental) {
        notificationService.sendRentalConfirmation(customer, rental);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment-confirmation")
    public ResponseEntity<Void> sendPaymentConfirmation(@RequestBody Customer customer, @RequestBody Payment payment) {
        notificationService.sendPaymentConfirmation(customer, payment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rental-reminder")
    public ResponseEntity<Void> sendRentalReminder(@RequestBody Customer customer, @RequestBody Rental rental) {
        notificationService.sendRentalReminder(customer, rental);
        return ResponseEntity.ok().build();
    }
}