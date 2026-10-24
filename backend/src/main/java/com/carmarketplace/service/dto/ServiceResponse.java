package com.carmarketplace.service.dto;

import java.math.BigDecimal;

public record ServiceResponse(
        Long id,
        String title,
        String slug,
        String category,
        String description,
        BigDecimal price
) {}