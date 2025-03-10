package com.leo.marketplace.controller;


import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.repository.UserRepository;
import com.leo.marketplace.service.CartItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cartItem")
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


    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/login";
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product does not exist"));

        try {
            cartItemService.addToCart(user, product, quantity);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return viewCart(session, model);
        }

        return "redirect:/cartItem";
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        List<CartItem> cartItemItems = cartItemService.getCartItem(user);
        model.addAttribute("cartItems", cartItemItems);
        model.addAttribute("userId", user.getId());

        return "cart";
    }

    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        cartItemService.removeCartItem(cartItemId);

        return "redirect:/cartItem";
    }

}
