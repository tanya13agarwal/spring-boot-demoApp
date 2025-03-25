package com.telusko.demoApp.repository;


import com.telusko.demoApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository                           // Class name with which we are working and Id types
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
    "LOWER (p.name) LIKE LOWER(CONCAT('%' , :Keyword , '%'))")
    List<Product> searchProducts(String keyword);
}
