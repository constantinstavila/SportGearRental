package com.sportgearrental.app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "First name is required")
    @Size(max=50, message = "First name must be les than 50 characters")
    private String firstName;

    @NotBlank(message= "Lats name is required")
    @Size(max=50, message = "First name must be les than 50 characters")
    private String lastName;

    @NotBlank(message ="Email is required")
    @Email(message = "Email must be valid")
    @Column (unique = true)
    private String email;

    @NotBlank(message= "Password is required")
    @Size(max=6, message = "Password must be al least 6 characters")
    private String password;

    @Size(max=10, message = "Address must be less than 100 characters")
    private String address;

    @NotBlank(message= "Phone number is required")
    @Pattern (regexp = "^\\\\+?[1-9]\\\\d{1,14}$", message = "Phone number must be valid" )
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;
}
