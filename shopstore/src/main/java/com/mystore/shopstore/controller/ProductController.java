package com.mystore.shopstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mystore.shopstore.model.Product;
import com.mystore.shopstore.repository.ProductRepository;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // POST - Add new product
    @PostMapping
    public Product creatProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // GET - get all product
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // GET by ID - get product by id
    @GetMapping("/{id}")
    public Product geProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
