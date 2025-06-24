package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.dto.CustomerDTO;
import com.sportgearrental.app.dto.PaymentDTO;
import com.sportgearrental.app.dto.RentalDTO;
import com.sportgearrental.app.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRentalConfirmation(CustomerDTO customer, RentalDTO rental) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Confirmare Închiriere");
        message.setText("Stimate " + customer.getFirstName() + ",\n\nÎnchirierea pentru echipamentul cu ID " + rental.getEquipmentId() + " a fost confirmată.\n\nMulțumim!");
        mailSender.send(message);
    }

    @Override
    public void sendPaymentConfirmation(CustomerDTO customer, PaymentDTO payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Confirmare Plată");
        message.setText("Stimate " + customer.getFirstName() + ",\n\nPlata de " + payment.getAmount() + " RON a fost procesată cu succes.\n\nMulțumim!");
        mailSender.send(message);
    }

    @Override
    public void sendRentalReminder(CustomerDTO customer, RentalDTO rental) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Memento Închiriere");
        message.setText("Stimate " + customer.getFirstName() + ",\n\nVă reamintim să returnați echipamentul cu ID " + rental.getEquipmentId() + " până la " + rental.getEndDate() + ".\n\nMulțumim!");
        mailSender.send(message);
    }
}