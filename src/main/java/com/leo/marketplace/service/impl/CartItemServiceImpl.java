package com.leo.marketplace.service.impl;

import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addProduct(Long Id, int quantity) {
        Product product = productRepository.findById(Id).orElseThrow(() -> new RuntimeException("Product does not exist"));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
    }
}
