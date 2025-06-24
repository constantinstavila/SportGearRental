package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.CustomerDTO;
import com.sportgearrental.app.dto.PaymentDTO;
import com.sportgearrental.app.dto.RentalDTO;

public interface NotificationService {
    void sendRentalConfirmation(CustomerDTO customer, RentalDTO rental);
    void sendPaymentConfirmation(CustomerDTO customer, PaymentDTO payment);
    void sendRentalReminder(CustomerDTO customer, RentalDTO rental);
}