package com.carmarketplace.order.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record OrderResponse(
        Long orderId,
        String status,
        BigDecimal totalAmount,
        String shippingAddress,
        String phone,
        OffsetDateTime createdAt
) {}