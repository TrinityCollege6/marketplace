package com.leo.marketplace.controller;


import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.repository.UserRepository;
import com.leo.marketplace.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

//    @PostMapping("/add/{Id}")
//    public String addProduct(@PathVariable Long Id, @RequestParam int quantity) {
//        cartService.addProduct(Id, quantity);
//        return "redirect:/cart";
//    }


    @PostMapping("/add/{productId}/{userId}")
    public String addToCart(@PathVariable Long productId, @PathVariable Long userId, @RequestParam int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return "redirect:/products/user/" + userId; // ✅ Redirect back to user product page
    }

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable Long userId, Model model) {
        List<CartItem> cartItemItems = cartItemRepository.findByUserId(userId);
        model.addAttribute("cartItems", cartItemItems);
        return "cart";
    }

}
