package com.product.product_spring.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class Product {

    private String id;
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}
