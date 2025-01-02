package com.zonaRefri.ecommerce.service;

import com.zonaRefri.ecommerce.dao.ProductRepository;
import com.zonaRefri.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable)
    { return productRepository.findBySubCategoryCategoryId(categoryId, pageable); } }