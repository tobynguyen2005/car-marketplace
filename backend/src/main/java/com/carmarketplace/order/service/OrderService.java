package com.carmarketplace.order.service;

import com.carmarketplace.accessory.entity.Accessory;
import com.carmarketplace.accessory.repository.AccessoryRepository;
import com.carmarketplace.cart.entity.Cart;
import com.carmarketplace.cart.entity.CartItem;
import com.carmarketplace.cart.repository.CartRepository;
import com.carmarketplace.order.dto.OrderRequest;
import com.carmarketplace.order.dto.OrderResponse;
import com.carmarketplace.order.entity.Order;
import com.carmarketplace.order.entity.OrderItem;
import com.carmarketplace.order.repository.OrderItemRepository;
import com.carmarketplace.order.repository.OrderRepository;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse checkout(String email, OrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot checkout.");
        }

        // Khởi tạo đơn hàng
        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.shippingAddress())
                .phone(request.phone())
                .totalAmount(BigDecimal.ZERO) // Sẽ tính toán lại ngay bên dưới
                .build();

        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // Duyệt qua từng món trong giỏ hàng để trừ kho (Áp dụng Khóa Bi Quan)
        for (CartItem cartItem : cart.getItems()) {
            // KHÓA DÒNG DỮ LIỆU: Chặn các thread khác update cùng một lúc
            Accessory accessory = accessoryRepository.findByIdForUpdate(cartItem.getAccessory().getId())
                    .orElseThrow(() -> new RuntimeException("Accessory not found"));

            if (accessory.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for: " + accessory.getTitle());
            }

            // Trừ số lượng kho
            accessory.setStockQuantity(accessory.getStockQuantity() - cartItem.getQuantity());
            accessoryRepository.save(accessory);

            // Tính tiền và tạo OrderItem (lưu dạng snapshot giá hiện tại)
            BigDecimal subTotal = accessory.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(subTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .accessory(accessory)
                    .productName(accessory.getTitle())
                    .unitPrice(accessory.getPrice())
                    .quantity(cartItem.getQuantity())
                    .subtotal(subTotal)
                    .build();

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        // Cập nhật lại tổng tiền đơn hàng
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // Xóa sạch giỏ hàng sau khi đặt thành công
        cart.getItems().clear();
        cartRepository.save(cart);

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getPhone(),
                order.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Page<Order> orders = orderRepository.findByUserId(user.getId(), pageable);
        return orders.map(order -> new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getPhone(),
                order.getCreatedAt()
        ));
    }
}