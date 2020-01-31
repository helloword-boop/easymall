package com.easymall.mapper;

import com.easymall.common.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    int queryProductCount();
    List<Product> queryProductByPage(Map<String, Integer> map);
    Product queryProductById(String productId);
    void addProduct(Product product);
    void updateProduct(Product product);
}
