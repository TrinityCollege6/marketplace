package com.leo.marketplace.controller;


import com.leo.marketplace.model.Order;
import com.leo.marketplace.model.User;
import com.leo.marketplace.model.response.OrderResponse;
import com.leo.marketplace.repository.OrderRepository;
import com.leo.marketplace.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place")
    public String orderPlace(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/user/login";
        }

        try {
            orderService.placeOrder(user);
        } catch (Exception e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/cartItem";
        }

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/user/login";
        }

        List<Order> orders = orderService.getOrderHistory(user);
        model.addAttribute("orders", orders);

        return "orderHistory";
    }

    @GetMapping("/all")
    @ResponseBody
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> new OrderResponse(
                        order.getId(),
                        order.getUser().getId(),
                        order.getTotalPrice(),
                        order.getStatus()
                )).toList();
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        try {
            order.setStatus(Order.OrderStatus.valueOf(status));
            orderRepository.save(order);
            return ResponseEntity.ok().body("Order status updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid order status");
        }
    }

}
