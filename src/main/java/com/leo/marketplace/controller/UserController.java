package com.leo.marketplace.controller;

import com.leo.marketplace.common.ErrorCode;
import com.leo.marketplace.exception.BusinessException;
import com.leo.marketplace.model.Order;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.model.request.UserRegisterRequest;
import com.leo.marketplace.repository.OrderRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.leo.marketplace.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    private static final String SALT = "leo";



    @GetMapping("/register")
    public String registerPage(){
        return "userRegister";
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> userRegister(@RequestBody UserRegisterRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid request data");
        }

        long userId = userService.userRegister(
                request.getUsername(),
                request.getFullname(),
                request.getEmail(),
                request.getPassword(),
                request.getCheckPassword()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("id", userId);
        response.put("message", "User registered successfully");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "userLogin";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User loginRequest, HttpSession session) {
        if (loginRequest == null || StringUtils.isAnyBlank(loginRequest.getUsername(), loginRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        User user = userOptional.get();

        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + loginRequest.getPassword()).getBytes());

        if (!encryptedPassword.equals(user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }
        session.setAttribute("user", user);

        String redirectUrl;
        if (user.getRole() == User.Role.CUSTOMER) {
            redirectUrl = "/products";
        } else if (user.getRole() == User.Role.ADMIN) {
            redirectUrl = "/user/admin/dashboard";
        } else {
            redirectUrl = "/user/login";
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "redirect", redirectUrl
        ));
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/user/login";
        }

        List<Product> products = productRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("orders", orders);

        return "adminDashboard";
    }
}
