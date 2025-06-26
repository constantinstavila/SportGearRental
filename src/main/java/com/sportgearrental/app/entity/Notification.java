package com.sportgearrental.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Message is required")
    @Column(name = "message")
    private String message;

    @Column(name = "sent_date")
    private LocalDateTime sentDate;

    @NotBlank(message = "Type is required")
    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}