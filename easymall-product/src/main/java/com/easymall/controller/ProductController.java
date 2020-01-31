package com.easymall.controller;

import com.easymall.common.pojo.Product;
import com.easymall.common.vo.EasyUIResult;
import com.easymall.common.vo.SysResult;
import com.easymall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("product/manage")
public class ProductController {
    @Autowired
    private ProductService productService;
    /*分页商品查询*/
    @RequestMapping("pageManage")
    public EasyUIResult queryPageProduct(int page,int rows){
        return productService.queryPageProduct(page,rows);
    }
    /*单项商品查询*/
    @RequestMapping("item/{productId}")
    public Product queryOneProduct(@PathVariable String productId){
        try {
            return productService.queryOneProduct(productId);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("缓存未命中");
            return null;
        }
    }
    /*新增商品*/
    @RequestMapping("save")
    public SysResult addProduct(Product product){
        System.out.println("save-----");
        try {
            productService.addProduct(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return new SysResult(201,"新增失败",null);
        }
    }
    /*更新商品*/
    @RequestMapping("update")
    public SysResult updateProduct(Product product){
        try {
            productService.updateProduct(product);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return new SysResult(201,"修改失败",null);
        }
    }
}
