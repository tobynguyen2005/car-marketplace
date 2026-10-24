package com.carmarketplace.user.dto;

public record AuthResponse(
        String accessToken,
        String email,
        String fullName,
        String role
) {}