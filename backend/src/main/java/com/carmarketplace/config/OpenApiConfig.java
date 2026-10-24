package com.carmarketplace.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Car Marketplace API",
                version = "1.0",
                description = "Tài liệu API chính thức cho hệ thống Car Marketplace. Hỗ trợ đầy đủ luồng E-commerce, Đặt lịch bảo dưỡng và Quản trị viên.",
                contact = @Contact(name = "Nguyen Huu Tai", email = "tai.nguyen@carmarketplace.com")
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Nhập JWT Token nhận được từ API đăng nhập vào đây. Format: Bearer <token> (Chỉ cần dán thẳng chuỗi token, hệ thống tự động thêm chữ Bearer).",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}