package com.carmarketplace.blog.dto;

import java.time.OffsetDateTime;

public record BlogResponse(
        Long id,
        String title,
        String slug,
        String content,
        String authorName,
        String status,
        OffsetDateTime createdAt
) {}