package com.carmarketplace.vehicle.service;

import com.carmarketplace.vehicle.dto.VehicleResponse;
import com.carmarketplace.vehicle.entity.Vehicle;
import com.carmarketplace.vehicle.repository.VehicleRepository;
import com.carmarketplace.vehicle.repository.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Page<VehicleResponse> searchVehicles(String brandSlug, String categorySlug, BigDecimal minPrice, BigDecimal maxPrice, Integer year, Pageable pageable) {
        Specification<Vehicle> spec = VehicleSpecification.filterVehicles(brandSlug, categorySlug, minPrice, maxPrice, year);
        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);
        return vehicles.map(this::mapToResponse);
    }

    public VehicleResponse getVehicleBySlug(String slug) {
        Vehicle vehicle = vehicleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return mapToResponse(vehicle);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getTitle(),
                vehicle.getSlug(),
                vehicle.getBrand().getName(),
                vehicle.getCategory().getName(),
                vehicle.getPrice(),
                vehicle.getYear(),
                vehicle.getFuelType(),
                vehicle.getTransmission(),
                vehicle.getMainImageUrl(),
                vehicle.getStatus().name()
        );
    }
}