package com.mystore.shopstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mystore.shopstore.model.Product;
import com.mystore.shopstore.repository.ProductRepository;

import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        product.setActive(true);

        return productRepository.save(product);
    }

    // GET - get all product that are Active
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll().stream().filter(Product::isActive).toList();
    }

    // GET - get all product for filter
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET by ID - get product by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Update Product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(new BigDecimal(price));

                    if (imageFile != null && !imageFile.isEmpty()) {
                        try {
                            String uploadDir = System.getProperty("user.dir") + "/uploads/";
                            Files.createDirectories(Paths.get(uploadDir));
                            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                            Path filePath = Paths.get(uploadDir + filename);
                            Files.copy(imageFile.getInputStream(), filePath,
                                    StandardCopyOption.REPLACE_EXISTING);
                            product.setImageUrl("/uploads/" + filename);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    return productRepository.save(product);
                })
                .orElse(null);

    }

    // Add Active/Deactive
    @PutMapping("/{id}/status")
    public ResponseEntity<Product> updateProductStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> payload) {
        return productRepository.findById(id).map(product -> {
            product.setActive(payload.get("active"));
            Product updated = productRepository.save(product);
            return ResponseEntity.ok(updated);
        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);

    }

}
