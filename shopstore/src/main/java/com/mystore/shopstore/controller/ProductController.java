package com.mystore.shopstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mystore.shopstore.model.Product;
import com.mystore.shopstore.repository.ProductRepository;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // POST - Add new product
    @PostMapping("/upload")
    public Product uploadProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // Path for saving img
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(uploadDir));

        // Build a unique name for img
        String filename = java.util.UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir + filename);
        java.nio.file.Files.copy(imageFile.getInputStream(), filePath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Build an Address in Database
        String imageUrl = "/uploads/" + filename;

        // Build and Save product
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(new java.math.BigDecimal(price));
        product.setImageUrl(imageUrl);

        return productRepository.save(product);
    }

    // GET - get all product
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // GET by ID - get product by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Product updatedProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setImageUrl(updatedProduct.getImageUrl());
                    return productRepository.save(product);
                })
                .orElse(null);

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);

    }

}
