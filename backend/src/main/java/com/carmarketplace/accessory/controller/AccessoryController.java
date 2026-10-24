package com.carmarketplace.accessory.controller;

import com.carmarketplace.accessory.dto.AccessoryResponse;
import com.carmarketplace.accessory.service.AccessoryService;
import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AccessoryResponse>>> getAccessories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<AccessoryResponse> accessoryPage = accessoryService.getAllAccessories(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(accessoryPage), "Accessories retrieved successfully"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<AccessoryResponse>> getAccessoryBySlug(@PathVariable String slug) {
        AccessoryResponse response = accessoryService.getAccessoryBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Accessory retrieved successfully"));
    }
}