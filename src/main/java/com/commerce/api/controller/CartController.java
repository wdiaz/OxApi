package com.commerce.api.controller;

import com.commerce.api.entity.Cart;
import com.commerce.api.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-item")
    public Cart addProductToCart(Long productId, Long merchantId) {
        return cartService.createCart(productId, merchantId);
    }
}
