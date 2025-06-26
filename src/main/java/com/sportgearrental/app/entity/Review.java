package com.sportgearrental.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @NotNull(message = "Customer is required")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "Equipment is required")
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
}