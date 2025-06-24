package com.sportgearrental.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDTO {
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Total cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total cost must be greater than 0")
    private BigDecimal totalCost;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must be less than 20 characters")
    private String status;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Equipment ID is required")
    private Long equipmentId;

    private Long paymentId;
}