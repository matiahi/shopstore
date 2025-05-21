package com.mystore.shopstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mystore.shopstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // To show active product for customer
    List<Product> findByActiveTrue();
}
