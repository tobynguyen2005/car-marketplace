package com.carmarketplace.order.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.order.dto.OrderRequest;
import com.carmarketplace.order.dto.OrderResponse;
import com.carmarketplace.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(
            Authentication authentication,
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.checkout(authentication.getName(), request);
        return ResponseEntity.ok(ApiResponse.success(response, "Order placed successfully"));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getMyOrders(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<OrderResponse> orderPage = orderService.getMyOrders(authentication.getName(), PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(orderPage), "Order history retrieved successfully"));
    }
}