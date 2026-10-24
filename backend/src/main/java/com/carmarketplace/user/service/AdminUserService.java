package com.carmarketplace.user.service;

import com.carmarketplace.user.dto.admin.AdminUserResponse;
import com.carmarketplace.user.dto.admin.UserStatusUpdateRequest;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional
    public AdminUserResponse updateUserStatus(Long userId, UserStatusUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(request.enabled());
        if (request.role() != null) {
            user.setRole(request.role());
        }

        userRepository.save(user);
        return mapToResponse(user);
    }

    private AdminUserResponse mapToResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getRole().name(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}