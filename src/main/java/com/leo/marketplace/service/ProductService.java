package com.leo.marketplace.service;

import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.request.ProductCreateRequest;
import com.leo.marketplace.model.request.ProductGetRequest;

import java.util.List;

public interface ProductService {
    public Product createProduct(ProductCreateRequest request);
    public List<Product> getAllProducts();
    public Product getProductById(Long id);


}
