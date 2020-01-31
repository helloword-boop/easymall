package com.easymall.service;

import com.easymall.common.pojo.Cart;
import com.easymall.common.pojo.Product;
import com.easymall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RestTemplate restTemplate;
    /*查询用户的购物车*/
    public List<Cart> queryCart(String userId) {
        return cartMapper.queryCartByUserId(userId);
    }

    /*往购物车新增商品*/
    public void addProductToCart(Cart cart) {
        Cart nowCart=cartMapper.queryCartByCart(cart);
        if(nowCart!=null){
            cart.setNum(cart.getNum()+nowCart.getNum());
            cartMapper.updateCartNum(cart);
        }else {
            String url="http://product-service/product/manage/item/"+cart.getProductId();
            Product product=restTemplate.getForObject(url,Product.class);
            cart.setProductImage(product.getProductImgurl());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductName(product.getProductName());
            cartMapper.addProductToCart(cart);
        }

    }

    /*修改购物车商品数量*/
    public void updateCartNum(Cart cart) {
        cartMapper.updateCartNum(cart);
    }

    /*删除购物车商品*/
    public void deleteProductFromCart(Cart cart) {
        cartMapper.deleteProductFromCart(cart);
    }
}
