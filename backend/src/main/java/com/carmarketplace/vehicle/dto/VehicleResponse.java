package com.carmarketplace.vehicle.dto;

import java.math.BigDecimal;

public record VehicleResponse(
        Long id,
        String title,
        String slug,
        String brandName,
        String categoryName,
        BigDecimal price,
        Integer year,
        String fuelType,
        String transmission,
        String mainImageUrl,
        String status
) {}