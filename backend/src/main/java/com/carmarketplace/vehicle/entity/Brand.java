package com.carmarketplace.vehicle.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class Brand extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "logo_url", length = 512)
    private String logoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();
}
