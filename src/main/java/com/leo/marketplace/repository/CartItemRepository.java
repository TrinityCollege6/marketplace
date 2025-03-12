package com.leo.marketplace.repository;

import com.leo.marketplace.model.CartItem;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    List<CartItem> findByUserId(Long userId);
    CartItem findByUserAndProduct(User user, Product product);
}
