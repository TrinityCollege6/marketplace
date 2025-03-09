package com.leo.marketplace.service;


import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;

import java.util.List;

public interface CartItemService {

    public void addToCart(User user, Product product, int quantity);
    public List<CartItem> getCartItem(User user);
    public void  removeCartItem(Long cartItemId);


}
