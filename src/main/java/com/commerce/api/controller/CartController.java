package com.commerce.api.controller;

import com.commerce.api.dto.cart.AddCartItemRequest;
import com.commerce.api.entity.Cart;
import com.commerce.api.entity.CartItem;
import com.commerce.api.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{uuid}/cart")
    public Cart getCartByUuid(@PathVariable String uuid) {
        return cartService.findByUuid(uuid);
    }

    @GetMapping("/{uuid}/items")
    public ResponseEntity<List<CartItem>> getItemsByCartUuid(@PathVariable String uuid) {
        Cart cart = cartService.findByUuid(uuid);
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/add-item")
    public Cart addProductToCart(
            @RequestBody AddCartItemRequest request
    ) {
        String uuid = request.getUuid();
        Long productId = request.getProductId();
        Integer quantity = request.getQuantity();
        return cartService.addCartItem(uuid, productId, quantity);
    }

    @DeleteMapping("/{uuid}/items/{productId}")
    public ResponseEntity<Map<String, Object>> deleteCartItem(@PathVariable String uuid, @PathVariable Long productId) {
        try {
            Map<String, Object> response = cartService.deleteCartItem(uuid, productId);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/update-item")
    public ResponseEntity<Map<String, Object>> updateCartItemQuantity( @RequestBody AddCartItemRequest request) {
        String uuid = request.getUuid();
        Long productId = request.getProductId();
        Integer quantity = request.getQuantity();

        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        try {
            Map<String, Object> response = cartService.updateCartItemQuantity(uuid, productId, quantity);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
