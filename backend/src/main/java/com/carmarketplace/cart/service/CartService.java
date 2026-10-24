package com.carmarketplace.cart.service;

import com.carmarketplace.accessory.entity.Accessory;
import com.carmarketplace.accessory.repository.AccessoryRepository;
import com.carmarketplace.cart.dto.CartItemRequest;
import com.carmarketplace.cart.dto.CartItemResponse;
import com.carmarketplace.cart.dto.CartResponse;
import com.carmarketplace.cart.entity.Cart;
import com.carmarketplace.cart.entity.CartItem;
import com.carmarketplace.cart.repository.CartItemRepository;
import com.carmarketplace.cart.repository.CartRepository;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartResponse addToCart(String email, CartItemRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy giỏ hàng của User (nếu chưa có thì tạo mới)
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        // Kiểm tra Accessory có tồn tại và còn hàng không
        Accessory accessory = accessoryRepository.findById(request.accessoryId())
                .orElseThrow(() -> new RuntimeException("Accessory not found"));

        if (accessory.getStockQuantity() < request.quantity()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // Kiểm tra xem item đã có trong giỏ chưa
        CartItem cartItem = cartItemRepository.findByCartIdAndAccessoryId(cart.getId(), accessory.getId())
                .orElse(CartItem.builder()
                        .cart(cart)
                        .accessory(accessory)
                        .quantity(0)
                        .build());

        // Cập nhật số lượng
        cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
        cartItemRepository.save(cartItem);

        return getCartByUserEmail(email);
    }

    @Transactional(readOnly = true)
    public CartResponse getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        // Lấy danh sách items và map sang DTO
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::mapToItemResponse)
                .toList();

        // Tính tổng tiền
        BigDecimal totalPrice = itemResponses.stream()
                .map(CartItemResponse::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), itemResponses, totalPrice);
    }

    private CartItemResponse mapToItemResponse(CartItem item) {
        BigDecimal unitPrice = item.getAccessory().getPrice();
        BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponse(
                item.getId(),
                item.getAccessory().getId(),
                item.getAccessory().getTitle(),
                item.getAccessory().getImageUrl(),
                unitPrice,
                item.getQuantity(),
                subTotal
        );
    }
}