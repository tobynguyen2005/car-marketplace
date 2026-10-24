package com.carmarketplace.service.dto.admin;

import com.carmarketplace.common.enums.ServiceBookingStatus;
import jakarta.validation.constraints.NotNull;

public record BookingStatusUpdateRequest(
        @NotNull(message = "Booking status is required")
        ServiceBookingStatus status
) {}