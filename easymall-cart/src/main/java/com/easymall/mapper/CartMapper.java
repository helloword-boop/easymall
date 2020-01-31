package com.easymall.mapper;

import com.easymall.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> queryCartByUserId(String userId);

    Cart queryCartByCart(Cart cart);

    void updateCartNum(Cart cart);

    void addProductToCart(Cart cart);

    void deleteProductFromCart(Cart cart);
}
