package com.carmarketplace.accessory.dto;

import java.math.BigDecimal;

public record AccessoryResponse(
        Long id,
        String title,
        String slug,
        String categoryName,
        BigDecimal price,
        Integer stockQuantity,
        String mainImageUrl,
        String description
) {}