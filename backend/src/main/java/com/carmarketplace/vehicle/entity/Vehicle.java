package com.carmarketplace.vehicle.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import com.carmarketplace.common.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private VehicleCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "fuel_type", nullable = false, length = 50)
    private String fuelType;

    @Column(nullable = false, length = 50)
    private String transmission;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "main_image_url", length = 512)
    private String mainImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Version
    @Column(nullable = false)
    private Long version = 0L;
}