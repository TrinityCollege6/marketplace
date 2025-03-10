package com.leo.marketplace.service.impl;

import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.CartItemRepository;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addToCart(User user, Product product, int quantity) {
        CartItem exsistedItem = cartItemRepository.findByUserAndProduct(user, product);
        if(product.getQuantity() == 0)
        {
            throw new IllegalArgumentException("Item is out of stock!");
        }
        if(product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available!");
        }
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
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Override
    public List<CartItem> getCartItem(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepository.save(product);

        cartItemRepository.deleteById(cartItemId);
    }

}
