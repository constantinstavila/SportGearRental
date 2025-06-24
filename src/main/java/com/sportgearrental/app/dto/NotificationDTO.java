package com.sportgearrental.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;

    @NotBlank(message = "Message is required")
    @Size(max = 500, message = "Message must be less than 500 characters")
    private String message;

    @NotNull(message = "Send date is required")
    private LocalDateTime sendDate;

    @NotBlank(message = "Type is required")
    @Size(max = 20, message = "Type must be less than 20 characters")
    private String type;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must be less than 20 characters")
    private String status;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}