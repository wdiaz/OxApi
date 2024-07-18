package com.commerce.api.controller;

import com.commerce.api.dto.cart.AddCartItemRequest;
import com.commerce.api.entity.Cart;
import com.commerce.api.entity.CartItem;
import com.commerce.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{uuid}/uuid")
    public Cart getCartByUuid(@PathVariable String uuid) {
        return cartService.findByUuid(uuid);
    }

    @GetMapping("/{uuid}/items")
    public ResponseEntity<List<CartItem>> getItemsByCartUuid(@PathVariable String uuid) {
        Cart cart = cartService.findByUuid(uuid);
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty() ) {
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
}
