package com.leo.marketplace.controller;


import com.leo.marketplace.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{Id}")
    public String addProduct(@PathVariable Long Id, @RequestParam int quantity) {
        cartService.addProduct(Id, quantity);
        return "redirect:/cart";
    }
}
