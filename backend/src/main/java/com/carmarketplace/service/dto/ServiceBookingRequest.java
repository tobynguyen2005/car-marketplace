package com.carmarketplace.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ServiceBookingRequest(
        @NotNull(message = "Service ID is required")
        Long serviceId,

        @NotBlank(message = "Customer name is required")
        String customerName,

        @NotBlank(message = "Phone number is required")
        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Booking date is required")
        @Future(message = "Booking date must be in the future")
        LocalDateTime bookingDate,

        String notes
) {}