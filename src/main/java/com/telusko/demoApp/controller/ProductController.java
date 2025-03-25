package com.telusko.demoApp.controller;

import com.telusko.demoApp.model.Product;
import com.telusko.demoApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ProductController {


    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(service.getProducts() , HttpStatus.OK);
    }

    @RequestMapping("/product/{prodId}")
    public ResponseEntity<Product> getProduct(@PathVariable int prodId) {
        Product prod = service.getProductById(prodId);

        if(prod != null) {
            return new ResponseEntity<>(prod , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/product")
            //<?> ye tb likhte hai jb hmm sure na ho ki kyaa return krr rhe
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                            @RequestPart MultipartFile imageFile ) {
                            //RequestPart is used to accept a part of object
                            //MultipartFile is used to accept image or any file

                            //Jo data body se aa rha hai usko map krke object mein rakh dega
        try {
            Product prod = service.addProduct(product , imageFile);
            return new ResponseEntity<>(prod , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product prod) {
        service.updateProduct(prod);
    }

    @DeleteMapping("/products/{prodId}")
    public void deleteProductById(@PathVariable int prodId) {
        service.deleteProductById(prodId);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product prod = service.getProductById(productId);
        byte[] imageFile = prod .getImage();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(prod.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{prodId}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodId,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile ) {
        Product prod = null;
        try {
            prod = service.updateProductById(prodId , product , imageFile);
        }
        catch(Exception e) {
            return new ResponseEntity<>("Failed to update" , HttpStatus.BAD_REQUEST);
        }

        if(prod != null) {
            return new ResponseEntity<>("Updated" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update" , HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int prodId) {
        Product prod = service.getProductById(prodId);
        if(prod != null) {
            service.deleteProduct(prodId);
            return new ResponseEntity<>("Deleted" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Product Not Found" , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String Keyword) {
        List<Product> prods = service.searchProducts(Keyword);
        return new ResponseEntity<>(prods , HttpStatus.OK);
    }
}