package com.leo.marketplace.service.impl;

import com.leo.marketplace.common.ErrorCode;
import com.leo.marketplace.exception.BusinessException;
import com.leo.marketplace.model.Product;
import com.leo.marketplace.model.request.ProductCreateRequest;
import com.leo.marketplace.model.request.ProductGetRequest;
import com.leo.marketplace.repository.ProductRepository;
import com.leo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductCreateRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Request body cannot be null");
        }
        if (StringUtils.isBlank(request.getName()) || request.getPrice() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Product name and price are required");
        }
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Price must be greater than 0");
        }
        if (productRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Product name already exists");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setHidden(request.getHidden() != null ? request.getHidden() : false); // Default false if null

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }


}
