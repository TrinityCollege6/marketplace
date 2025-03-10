package com.leo.marketplace.controller;


import com.leo.marketplace.model.Order;
import com.leo.marketplace.model.User;
import com.leo.marketplace.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

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

}
