package com.carmarketplace.vehicle.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import com.carmarketplace.vehicle.dto.VehicleResponse;
import com.carmarketplace.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<VehicleResponse> vehiclePage = vehicleService.searchVehicles(brand, category, minPrice, maxPrice, year, PageRequest.of(page, size, sort));

        PageResponse<VehicleResponse> pageResponse = PageResponse.from(vehiclePage);

        return ResponseEntity.ok(ApiResponse.success(pageResponse, "Vehicles retrieved successfully"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicleBySlug(@PathVariable String slug) {
        VehicleResponse response = vehicleService.getVehicleBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Vehicle retrieved successfully"));
    }
}