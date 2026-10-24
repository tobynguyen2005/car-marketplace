package com.carmarketplace.accessory.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accessories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accessory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private AccessoryCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Version
    @Column(nullable = false)
    private Long version = 0L;
}