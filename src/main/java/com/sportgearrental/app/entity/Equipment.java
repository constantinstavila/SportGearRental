package com.sportgearrental.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipments")
@Getter
@Setter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Price per day is required")
    @Column(name = "price_per_day")
    private BigDecimal pricePerDay;

    @NotNull(message = "Availability is required")
    @Column(name = "available")
    private boolean available;

    @Column(name = "equipment_condition")
    private String equipmentCondition;

    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}