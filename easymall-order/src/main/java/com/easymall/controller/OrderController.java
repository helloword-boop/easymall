package com.easymall.controller;

import com.easymall.common.pojo.Order;
import com.easymall.common.vo.SysResult;
import com.easymall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order/manage")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("query/{userId}")
    public List<Order> queryOrder(@PathVariable String userId){
        System.out.println(userId);
        return orderService.queryOrder(userId);
    }
    @RequestMapping("delete/{orderId}")
    public SysResult deleteOrder(@PathVariable String orderId){
        try {
            orderService.deleteOrder(orderId);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"fail",null);
        }
    }
    @RequestMapping("save")
    public SysResult addOrder(Order order){
        try {
            orderService.addOrder(order);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"fail",null);
        }
    }
}
