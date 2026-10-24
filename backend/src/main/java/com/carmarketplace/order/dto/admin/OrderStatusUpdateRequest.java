package com.carmarketplace.order.dto.admin;

import com.carmarketplace.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(
        @NotNull(message = "Order status is required")
        OrderStatus status
) {}