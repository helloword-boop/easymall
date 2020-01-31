package com.easymall.service;

import com.easymall.common.pojo.Product;
import com.easymall.common.utils.MapperUtil;
import com.easymall.common.vo.EasyUIResult;
import com.easymall.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private JedisCluster jedisCluster;
    /*分页查询商品*/
    public EasyUIResult queryPageProduct(int page, int rows) {
        int start=(page-1)*rows;
        int total=productMapper.queryProductCount();
        Map<String,Integer> map = new HashMap<>();
        map.put("start",start);
        map.put("rows",rows);
        List<Product> productList=productMapper.queryProductByPage(map);
        return new EasyUIResult(total,productList);
    }
    /*单项商品查询*/
    public Product queryOneProduct(String productId) throws IOException {
        String lock=productId+".lock";
        if(jedisCluster.exists(lock)){
            return productMapper.queryProductById(productId);
        }
        String productKey="product_query_"+productId;
        if(jedisCluster.exists(productKey)){
            String productJson=jedisCluster.get(productKey);
            return MapperUtil.MP.readValue(productJson,Product.class);
        }else {
            Product product=productMapper.queryProductById(productId);
            String productJson=MapperUtil.MP.writeValueAsString(product);
            jedisCluster.setex(productKey,2*60*60,productJson);
            return product;
        }
    }
    /*新增商品*/
    public void addProduct(Product product) throws JsonProcessingException {
        product.setProductId(UUID.randomUUID().toString());
        String productJson=MapperUtil.MP.writeValueAsString(product);
        String productKey="product_query_"+product.getProductId();
        jedisCluster.setex(productKey,60*60*2,productJson);
        productMapper.addProduct(product);
    }
    /*更新商品*/
    public void updateProduct(Product product) {
        String lock=product.getProductId()+".lock";
        String productKey="product_query_"+product.getProductId();
        jedisCluster.set(lock,"");
        jedisCluster.del(productKey);
        productMapper.updateProduct(product);
        jedisCluster.del(lock);
    }
}
