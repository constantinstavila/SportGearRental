package com.sportgearrental.app.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Added customer relationship
    private Customer customer;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne(mappedBy = "rental", cascade = CascadeType.ALL)
    private Payment payment;
}