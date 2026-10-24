package com.carmarketplace.cart.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long accessoryId,
        String accessoryName,
        String imageUrl,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subTotal
) {}