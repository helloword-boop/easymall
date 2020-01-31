package com.easymall.mapper;

import com.easymall.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> queryOrder(String userId);
    void deleteOrder(String orderId);
    void addOrder(Order order);
}
