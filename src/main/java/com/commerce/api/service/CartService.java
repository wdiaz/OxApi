package com.commerce.api.service;

import com.commerce.api.entity.Cart;
import com.commerce.api.entity.CartItem;
import com.commerce.api.entity.Merchant;
import com.commerce.api.entity.Product;
import com.commerce.api.repository.CartRepository;
import com.commerce.api.repository.MerchantRepository;
import com.commerce.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    ProductRepository productRepository;

    MerchantRepository merchantRepository;

    CartRepository cartRepository;

    @Autowired
    public CartService(ProductRepository productRepository, MerchantRepository merchantRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
        this.cartRepository = cartRepository;
    }

    /*public Cart createCart(Long productId, Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        UUID uuid = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUuid(uuid.toString());
        cart.setStatus(Cart.CREATED);
        cart.setSession("34523523523532453352");
        cartRepository.save(cart);
        return cart;
    }*/

    public Cart findByUuid(String uuid) {
        return cartRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Transactional
    public Cart addCartItem(String uuid, Long productId, Integer quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUuid(uuid);
        Cart cart = optionalCart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUuid(UUID.randomUUID().toString());
            newCart.setStatus(Cart.CREATED);


            UUID randomUUID = UUID.randomUUID();
            newCart.setSession(randomUUID.toString());
            return newCart;
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

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

        /*UUID randomUUID = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUuid(randomUUID.toString());
        cart.setStatus(Cart.CREATED);
        cart.setSession("34523523523532453352");
        cartRepository.save(cart);
        return cart;*/
    }
}
