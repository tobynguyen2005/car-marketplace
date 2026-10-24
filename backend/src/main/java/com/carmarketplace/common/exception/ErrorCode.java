package com.carmarketplace.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid email or password", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email is already registered", HttpStatus.CONFLICT),
    UNAUTHORIZED("UNAUTHORIZED", "Authentication required", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "Access denied", HttpStatus.FORBIDDEN),

    ACCESSORY_NOT_FOUND("ACCESSORY_NOT_FOUND", "Accessory not found", HttpStatus.NOT_FOUND),
    OUT_OF_STOCK("OUT_OF_STOCK", "Product is out of stock", HttpStatus.CONFLICT),
    STOCK_INSUFFICIENT("STOCK_INSUFFICIENT", "Insufficient stock for the requested quantity", HttpStatus.CONFLICT),
    STOCK_CONFLICT("STOCK_CONFLICT", "The product stock was changed by another transaction. Please try again.", HttpStatus.CONFLICT),
    CONCURRENCY_CONFLICT("CONCURRENCY_CONFLICT", "A concurrent update conflict occurred. Please try again.", HttpStatus.CONFLICT),

    CART_NOT_FOUND("CART_NOT_FOUND", "Shopping cart not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND("CART_ITEM_NOT_FOUND", "Cart item not found", HttpStatus.NOT_FOUND),

    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Order not found", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_BE_CANCELLED("ORDER_CANNOT_BE_CANCELLED", "Order cannot be cancelled in its current status", HttpStatus.BAD_REQUEST),

    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED", "Too many requests. Please try again later.", HttpStatus.TOO_MANY_REQUESTS),

    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
