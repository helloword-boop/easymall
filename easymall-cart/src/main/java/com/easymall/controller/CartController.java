package com.easymall.controller;

import com.easymall.common.pojo.Cart;
import com.easymall.common.vo.SysResult;
import com.easymall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cart/manage")
public class CartController {
    @Autowired
    CartService cartService;
    @RequestMapping("query")
    public List<Cart> queryCart(String userId){
        System.out.println(userId);
        return cartService.queryCart(userId);
    }
    @RequestMapping("save")
    public SysResult addProductToCart(Cart cart){
        try {
            cartService.addProductToCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"exception",null);
        }
    }
    @RequestMapping("update")
    public SysResult updateCartNum(Cart cart){
        try {
            cartService.updateCartNum(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"excption",null);
        }
    }
    @RequestMapping("delete")
    public SysResult deleteProductFromCart(Cart cart){
        try {
            cartService.deleteProductFromCart(cart);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"excption",null);
        }
    }
}
