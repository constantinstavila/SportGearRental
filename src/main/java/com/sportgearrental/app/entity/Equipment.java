package com.sportgearrental.app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size (max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size (max=500, message= "Description must be less than 500 characters")
    private String description;

    @ManyToOne ( fetch = FetchType.LAZY)
    @JoinColumn (name ="category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;              //category class....

    @NotNull(message = "Price per day is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal pricePerDay;

    private boolean available = true;

    @NotBlank(message = "Conditions required")
    @Size (max=50, message="Condition must be less than 50 characters")
    private String equipmentCondition;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Review> reviews;

}
