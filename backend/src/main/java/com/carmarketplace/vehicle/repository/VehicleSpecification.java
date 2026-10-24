package com.carmarketplace.vehicle.repository;

import com.carmarketplace.common.enums.VehicleStatus;
import com.carmarketplace.vehicle.entity.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> filterVehicles(String brandSlug, String categorySlug, BigDecimal minPrice, BigDecimal maxPrice, Integer year) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo Brand Slug
            if (brandSlug != null && !brandSlug.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("slug"), brandSlug));
            }
            // Lọc theo Category Slug
            if (categorySlug != null && !categorySlug.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("slug"), categorySlug));
            }
            // Lọc theo khoảng giá
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            // Lọc theo năm sản xuất
            if (year != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), year));
            }

            // Khách hàng vãng lai chỉ được xem xe đang có sẵn (AVAILABLE)
            predicates.add(criteriaBuilder.equal(root.get("status"), VehicleStatus.AVAILABLE));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}