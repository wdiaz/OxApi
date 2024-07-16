package com.commerce.api.controller;

import com.commerce.api.entity.Product;
import com.commerce.api.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    public final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    List<Product> list() {
        //List<Product> products = new ArrayList<Product>();
        return this.productRepository.findAll();
    }

    @PostMapping("/products")
    Product create(@RequestBody Product product) {
        return this.productRepository.save(product);
    }
}
