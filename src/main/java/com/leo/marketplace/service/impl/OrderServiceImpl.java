package com.leo.marketplace.service.impl;

import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Order;
import com.leo.marketplace.model.OrderedItem;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.OrderRepository;
import com.leo.marketplace.repository.OrderedItemRepository;
import com.leo.marketplace.service.OrderService;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderedItemRepository orderedItemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if(cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.ORDERED);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderedItem> orderedItems = new ArrayList<>();

        for(CartItem cartItem : cartItems) {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setOrder(order);
            orderedItem.setProduct(cartItem.getProduct());
            orderedItem.setQuantity(cartItem.getQuantity());
            orderedItem.setPurchasePrice(cartItem.getProduct().getPrice());

            totalPrice = totalPrice.add(cartItem.getProduct().getPrice()).multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            orderedItems.add(orderedItem);
        }

        order.setOrderedItems(orderedItems);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
        orderedItemRepository.saveAll(orderedItems);

        cartItemRepository.deleteAll(cartItems);

        return order;
    }


    @Override
    public List<Order> getOrderHistory(User user) {
        return orderRepository.findByUser(user);
    }

}
