package com.leo.marketplace.service;

import com.leo.marketplace.model.Order;
import com.leo.marketplace.model.User;

import java.util.List;

public interface OrderService {
    public Order placeOrder(User user);

    public List<Order> getOrderHistory(User user);
}
