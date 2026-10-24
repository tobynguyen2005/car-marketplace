package com.carmarketplace.contact.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.contact.dto.ContactRequest;
import com.carmarketplace.contact.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> submitContactMessage(@Valid @RequestBody ContactRequest request) {
        contactService.submitMessage(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Message submitted successfully. We will get back to you soon."));
    }
}