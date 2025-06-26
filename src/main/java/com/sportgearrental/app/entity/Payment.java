package com.sportgearrental.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @Column(name = "amount")
    private BigDecimal amount;

    @NotBlank(message = "Method is required")
    @Column(name = "method")
    private String method;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @NotBlank(message = "Status is required")
    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
}