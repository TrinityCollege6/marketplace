package com.leo.marketplace.controller;

import com.leo.marketplace.common.ErrorCode;
import com.leo.marketplace.exception.BusinessException;
import com.leo.marketplace.model.User;
import com.leo.marketplace.model.request.UserRegisterRequest;
import com.leo.marketplace.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.leo.marketplace.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequestMapping("/user")
//@CrossOrigin(origins = "*")
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Username and password are required");
        }

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(loginRequest.getPassword())) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "Invalid username or password");
        }
        User user = userOptional.get();
        session.setAttribute("user", user);

        if(user.getRole() == User.Role.CUSTOMER) {
            String redirectUrl = "/products";
            return ResponseEntity.ok(Map.of("redirect", redirectUrl));
        }

        return ResponseEntity.ok(userOptional.get());
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
