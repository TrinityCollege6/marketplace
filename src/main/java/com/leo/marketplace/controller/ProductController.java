package com.leo.marketplace.controller;

import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.request.ProductCreateRequest;
import com.leo.marketplace.model.request.ProductGetRequest;
import com.leo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> productCreate(@RequestBody ProductCreateRequest request) {
        Product product = productService.createProduct(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", product.getId());
        response.put("message", "Product created successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "productPage";
    }

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }


}
