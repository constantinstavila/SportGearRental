package com.sportgearrental.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer is required")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "Equipment is required")
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Transient
    private String equipmentName;
}