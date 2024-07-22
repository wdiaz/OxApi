package com.commerce.api.service.cart;

import com.commerce.api.entity.Cart;
import com.commerce.api.entity.CartItem;
import com.commerce.api.entity.Product;
import com.commerce.api.repository.CartRepository;
import com.commerce.api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.*;

@Service
public class CartService {

    ProductRepository productRepository;

    CartRepository cartRepository;

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    public CartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }
    public Cart findByUuid(String uuid) {
        return cartRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Transactional
    public Cart addCartItem(String uuid, Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Optional<Cart> optionalCart = cartRepository.findByUuid(uuid);

        Cart cart = optionalCart.orElseGet(() -> {
            Cart newCart = CartFactory.createNew();
            newCart.setUuid(uuid);
            newCart.setStatus(Cart.CREATED);
            UUID randomUUID = UUID.randomUUID();
            newCart.setSession(randomUUID.toString());
            return newCart;
        });

        // Check if the product already exists in the cart
        boolean productExists = cart.getCartItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));

        if (!productExists) {
            // Create a new CartItem and add it to the cart
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
            /**
             * @TODO: Validate quantity against inventory
             */
            cartItem.setQuantity(quantity);
            cart.addCartItem(cartItem);
        } else {
            /**
             * @TODO: No need to loop again. Move this code above and adjust accordingly
             */
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setCart(cart);
            cart.addCartItem(cartItem);
            cartItem.setUpdatedAt(Instant.now());

        }
        cartRepository.save(cart);
        return cart;
    }


    public Map<String, Object> deleteCartItem(String cartUuid, Long productId) {
        Cart cart = findByUuid(cartUuid);

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        //cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (existingCartItem.isPresent()) {
            return getStringObjectMap(cart, existingCartItem);
        } else {
            throw new NoSuchElementException("Item not found in cart");
        }
    }

    public Map<String, Object> updateCartItemQuantity(String cartUuid, Long productId, Integer quantity) {
        Cart cart = findByUuid(cartUuid);
        if(null == cart) {
            throw new NoSuchElementException("Invalid Cart");
        }
        if (cart.getCartItems().isEmpty()) {
            throw new NoSuchElementException("Item not found in cart");
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            int currentQuantity = existingCartItem.get().getQuantity();
            if(quantity == 0) {
                return getStringObjectMap(cart, existingCartItem);
            }
            int newQuantity = currentQuantity + quantity;
            existingCartItem.get().setQuantity(newQuantity);
            existingCartItem.get().setUpdatedAt(Instant.now());

            cartRepository.save(cart);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item updated successfully");
            response.put("cart", cart);
            return response;
        } else {
            throw new NoSuchElementException("Item not found in cart");
        }
    }

    private Map<String, Object> getStringObjectMap(Cart cart, Optional<CartItem> existingCartItem) {
        CartItem cartItem = existingCartItem.get();
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Item deleted successfully");
        response.put("cart", cart);
        return response;
    }
}
