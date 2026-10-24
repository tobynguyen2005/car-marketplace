package com.carmarketplace.service.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record ServiceBookingResponse(
        Long bookingId,
        String serviceTitle,
        String customerName,
        String phone,
        String email,
        LocalDateTime bookingDate,
        String status,
        String notes,
        OffsetDateTime createdAt
) {}