package com.leo.marketplace.repository;

import com.leo.marketplace.model.OrderedItem;
import com.leo.marketplace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
    List<OrderedItem> findByOrder(Order order);
}
