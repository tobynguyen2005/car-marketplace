package com.carmarketplace.service.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.service.dto.ServiceBookingRequest;
import com.carmarketplace.service.dto.ServiceBookingResponse;
import com.carmarketplace.service.dto.ServiceResponse;
import com.carmarketplace.service.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ServiceResponse>>> getServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<ServiceResponse> servicePage = maintenanceService.getAllServices(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(servicePage), "Services retrieved successfully"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceBySlug(@PathVariable String slug) {
        ServiceResponse response = maintenanceService.getServiceBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Service retrieved successfully"));
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<ServiceBookingResponse>> createBooking(
            Authentication authentication,
            @Valid @RequestBody ServiceBookingRequest request) {

        String email = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : null;
        ServiceBookingResponse response = maintenanceService.createBooking(request, email);

        return ResponseEntity.ok(ApiResponse.success(response, "Service booked successfully"));
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PageResponse<ServiceBookingResponse>>> getMyBookings(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<ServiceBookingResponse> bookingPage = maintenanceService.getMyBookings(authentication.getName(), PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(bookingPage), "Bookings retrieved successfully"));
    }
}