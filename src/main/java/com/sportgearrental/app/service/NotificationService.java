package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Payment;
import com.sportgearrental.app.entity.Rental;

public interface NotificationService {


    void sendRentalConfirmation(Customer customer, Rental rental);
    void sendPaymentConfirmation(Customer customer, Payment payment);
    void sendRentalReminder(Customer customer, Rental rental);


}