package com.product.product_spring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.product.product_spring.dto.Product;
import com.product.product_spring.mapper.ProductMapper;




@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<Product> getAll() {
        return productMapper.selectAll();
    }
    
    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") String id) {
        
        return productMapper.selectOne(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        product.setId(UUID.randomUUID().toString());
        productMapper.insert(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") String id, @RequestBody Product product) {
        product.setId(id);
        productMapper.update(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id){
        productMapper.delete(id);
    } 
}
