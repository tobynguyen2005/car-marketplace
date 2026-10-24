package com.carmarketplace.user.dto.admin;

import java.time.OffsetDateTime;

public record AdminUserResponse(
        Long id,
        String email,
        String fullName,
        String phone,
        String role,
        boolean enabled,
        OffsetDateTime createdAt
) {}