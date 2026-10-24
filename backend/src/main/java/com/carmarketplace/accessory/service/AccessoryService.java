package com.carmarketplace.accessory.service;

import com.carmarketplace.accessory.dto.AccessoryResponse;
import com.carmarketplace.accessory.entity.Accessory;
import com.carmarketplace.accessory.repository.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public Page<AccessoryResponse> getAllAccessories(Pageable pageable) {
        return accessoryRepository.findAll(pageable).map(this::mapToResponse);
    }

    public AccessoryResponse getAccessoryBySlug(String slug) {
        Accessory accessory = accessoryRepository.findAll().stream()
                .filter(a -> a.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Accessory not found"));
        return mapToResponse(accessory);
    }

    private AccessoryResponse mapToResponse(Accessory accessory) {
        return new AccessoryResponse(
                accessory.getId(),
                accessory.getTitle(),
                accessory.getSlug(),
                accessory.getCategory() != null ? accessory.getCategory().getName() : "Uncategorized",
                accessory.getPrice(),
                accessory.getStockQuantity(),
                accessory.getImageUrl(),
                accessory.getDescription()
        );
    }
}