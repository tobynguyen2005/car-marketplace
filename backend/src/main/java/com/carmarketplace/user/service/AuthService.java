package com.carmarketplace.user.service;

import com.carmarketplace.common.enums.UserRole;
import com.carmarketplace.security.JwtTokenProvider;
import com.carmarketplace.user.dto.AuthResponse;
import com.carmarketplace.user.dto.LoginRequest;
import com.carmarketplace.user.dto.RegisterRequest;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        // Xác thực bằng Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Nếu thành công, sinh token và lấy thông tin user
        String accessToken = tokenProvider.generateAccessToken(authentication);
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(accessToken, user.getEmail(), user.getFullName(), user.getRole().name());
    }

    public String getRefreshToken(String email) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
        return tokenProvider.generateRefreshToken(authentication);
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .phone(request.phone())
                .role(UserRole.USER) // Role mặc định
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}