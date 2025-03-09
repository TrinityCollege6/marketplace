package com.leo.marketplace.service.impl;

import com.leo.marketplace.model.Cart;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.repository.CartRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.CartService;
import com.leo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addProduct(Long Id, int quantity) {
        Product product = productRepository.findById(Id).orElseThrow(() -> new RuntimeException("Product does not exist"));
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(quantity);

        cartRepository.save(cart);
    }
}
