package com.telusko.demoApp.service;

import com.telusko.demoApp.model.Product;
import com.telusko.demoApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

//    List<Product> products = new ArrayList<>(Arrays.asList(
//            new Product(101 , "Iphone" , 50000) ,
//            new Product(102 , "Canon Camera" , 70000),
//            new Product(103 , "DSLR" , 100000)
//    ));

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductById(int prodId) {
        return repo.findById(prodId).orElse(new Product());
    }

    public Product addProduct(Product prod , MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImage(imageFile.getBytes());
        return repo.save(prod);
    }

    public void updateProduct(Product prod) {
        repo.save(prod);//It will update or else create a new one
    }

    public void deleteProductById(int prodId) {
        repo.deleteById(prodId);
    }

    public Product updateProductById(int prodId, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImage(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
