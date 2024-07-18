package com.commerce.api.service;

import com.commerce.api.entity.Cart;
import com.commerce.api.entity.Merchant;
import com.commerce.api.entity.Product;
import com.commerce.api.repository.CartRepository;
import com.commerce.api.repository.MerchantRepository;
import com.commerce.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cart createCart(Long productId, Long merchantId) {
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
    }

    public Cart findByUuid(String uuid) {
        return cartRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    /*public Cart findItemsByCartUid(String uuid) {

    }*/
}
