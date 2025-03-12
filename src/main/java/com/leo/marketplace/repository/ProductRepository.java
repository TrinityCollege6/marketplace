package com.leo.marketplace.repository;

import com.leo.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsHiddenFalse();
    List<Product> findAll();
    Optional<Product> findByName(String name);
    boolean existsByName(String name);

}
