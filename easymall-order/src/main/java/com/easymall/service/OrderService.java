package com.easymall.service;

import com.easymall.common.pojo.Order;
import com.easymall.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public List<Order> queryOrder(String userId) {
        return orderMapper.queryOrder(userId);
    }

    public void deleteOrder(String orderId) {
        orderMapper.deleteOrder(orderId);
    }

    public void addOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderPaystate(0);
        order.setOrderTime(new Date());
        orderMapper.addOrder(order);
    }
}
