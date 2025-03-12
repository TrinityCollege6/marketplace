package com.leo.marketplace.controller;

import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.model.request.ProductCreateRequest;
import com.leo.marketplace.model.request.ProductGetRequest;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> productCreate(@RequestBody ProductCreateRequest request) {
        Product product = productService.createProduct(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", product.getId());
        response.put("message", "Product created successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (updates.containsKey("price")) {
            product.setPrice(new BigDecimal(updates.get("price").toString()));
        }
        if (updates.containsKey("quantity")) {
            product.setQuantity((Integer) updates.get("quantity"));
        }
        if (updates.containsKey("description")) {
            product.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("imageUrl")) {
            product.setImageUrl((String) updates.get("imageUrl"));
        }

        productRepository.save(product);
        return ResponseEntity.ok().body("Product updated successfully");
    }


    @PostMapping("/hide/{id}")
    @ResponseBody
    public ResponseEntity<?> hideProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setHidden(true);
        productRepository.save(product);

        return ResponseEntity.ok().body("Product hidden successfully");
    }

    @PostMapping("/unhide/{id}")
    @ResponseBody
    public ResponseEntity<?> unhideProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setHidden(false);
        productRepository.save(product);

        return ResponseEntity.ok().body("Product unhidden successfully");
    }

    @GetMapping
    public String productPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("userId", user.getId());
        return "productPage";
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("userId", user.getId());
        return "productDetails";
    }


}
