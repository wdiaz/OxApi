package com.commerce.api.service.cart;

import com.commerce.api.entity.Cart;

import java.util.UUID;

public class CartFactory {

    public static Cart createNew () {
        return new Cart();
    }
}
