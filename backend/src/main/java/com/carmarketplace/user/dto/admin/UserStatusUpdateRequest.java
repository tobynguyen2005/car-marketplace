package com.carmarketplace.user.dto.admin;

import com.carmarketplace.common.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateRequest(
        @NotNull(message = "Enabled status is required")
        Boolean enabled,

        UserRole role
) {}