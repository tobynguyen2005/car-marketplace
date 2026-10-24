package com.carmarketplace.user.controller;

import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.user.dto.AuthResponse;
import com.carmarketplace.user.dto.LoginRequest;
import com.carmarketplace.user.dto.RegisterRequest;
import com.carmarketplace.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        String refreshToken = authService.getRefreshToken(request.email());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(refreshToken).toString())
                .body(ApiResponse.success(authResponse, "Login successful"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);

        return ResponseEntity.ok()
                .body(ApiResponse.success(null, "Registration successful. Please login to continue."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // Xóa cookie bằng cách set Max-Age = 0
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.success(null, "Logout successful"));
    }

    // Helper tạo Cookie
    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)      // Chống XSS (JavaScript không đọc được)
                .secure(true)        // Yêu cầu HTTPS (khi lên production)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 ngày
                .sameSite("Strict")
                .build();
    }
}