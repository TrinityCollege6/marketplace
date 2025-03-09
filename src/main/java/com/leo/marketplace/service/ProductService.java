package com.leo.marketplace.service;

import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.request.ProductCreateRequest;
import com.leo.marketplace.model.request.ProductGetRequest;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductCreateRequest request);
    List<Product> getAllProducts();
    Product getProductById(Long id);


}
