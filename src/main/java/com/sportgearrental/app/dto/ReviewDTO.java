package com.sportgearrental.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;

    @NotNull(message = "Review date is required")
    private LocalDateTime reviewDate;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Equipment ID is required")
    private Long equipmentId;
}