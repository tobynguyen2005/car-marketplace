package com.carmarketplace.user.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.user.dto.admin.AdminUserResponse;
import com.carmarketplace.user.dto.admin.UserStatusUpdateRequest;
import com.carmarketplace.user.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminUserResponse>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<AdminUserResponse> users = adminUserService.getAllUsers(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(users), "Users retrieved successfully"));
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UserStatusUpdateRequest request) {

        AdminUserResponse response = adminUserService.updateUserStatus(userId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "User status updated successfully"));
    }
}