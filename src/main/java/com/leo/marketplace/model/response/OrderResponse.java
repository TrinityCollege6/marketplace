package com.leo.marketplace.model.response;

import com.leo.marketplace.model.Order.OrderStatus;

import java.math.BigDecimal;

public class OrderResponse {

    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private OrderStatus status;

    public OrderResponse(Long id, Long userId, BigDecimal totalPrice, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
