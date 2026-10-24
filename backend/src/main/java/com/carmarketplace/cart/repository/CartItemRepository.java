package com.carmarketplace.cart.repository;

import com.carmarketplace.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndAccessoryId(Long cartId, Long accessoryId);
}