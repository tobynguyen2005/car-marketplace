package com.carmarketplace.order.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderRequest(
        @NotBlank(message = "Shipping address is required")
        String shippingAddress,

        @NotBlank(message = "Phone number is required")
        String phone,

        String notes
) {}