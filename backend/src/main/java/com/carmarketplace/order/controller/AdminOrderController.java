package com.carmarketplace.order.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.order.dto.OrderResponse;
import com.carmarketplace.order.dto.admin.OrderStatusUpdateRequest;
import com.carmarketplace.order.service.AdminOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<OrderResponse> orders = adminOrderService.getAllOrders(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(orders), "Orders retrieved successfully"));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request) {

        OrderResponse response = adminOrderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Order status updated successfully"));
    }
}