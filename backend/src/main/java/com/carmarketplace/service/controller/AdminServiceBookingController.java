package com.carmarketplace.service.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.service.dto.ServiceBookingResponse;
import com.carmarketplace.service.dto.admin.BookingStatusUpdateRequest;
import com.carmarketplace.service.service.AdminServiceBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/service-bookings")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminServiceBookingController {

    private final AdminServiceBookingService adminServiceBookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ServiceBookingResponse>>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<ServiceBookingResponse> bookings = adminServiceBookingService.getAllBookings(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(bookings), "Bookings retrieved successfully"));
    }

    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<ApiResponse<ServiceBookingResponse>> updateBookingStatus(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingStatusUpdateRequest request) {

        ServiceBookingResponse response = adminServiceBookingService.updateBookingStatus(bookingId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Booking status updated successfully"));
    }
}