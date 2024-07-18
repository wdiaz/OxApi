package com.commerce.api.repository;

import com.commerce.api.entity.Cart;
import com.commerce.api.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUuid(String uuid);
}
