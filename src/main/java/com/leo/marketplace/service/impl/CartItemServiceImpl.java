package com.leo.marketplace.service.impl;

import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addToCart(User user, Product product, int quantity) {
        CartItem exsistedItem = cartItemRepository.findByUserAndProduct(user, product);
        if(exsistedItem != null) {
            exsistedItem.setQuantity(exsistedItem.getQuantity() + quantity);
            cartItemRepository.save(exsistedItem);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItem> getCartItem(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
