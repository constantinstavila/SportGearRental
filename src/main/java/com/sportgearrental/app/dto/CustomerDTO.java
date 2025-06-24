package com.sportgearrental.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required", groups = CreateValidation.class)
    @Size(min = 6, message = "Password must be at least 6 characters", groups = CreateValidation.class)
    private String password;

    @Size(max = 100, message = "Address must be less than 100 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must be valid")
    private String phoneNumber;

    interface CreateValidation {}
}