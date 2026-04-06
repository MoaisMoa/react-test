package com.product.product_spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.product.product_spring.dto.Product;

@Mapper
public interface ProductMapper {
    List<Product> selectAll();      // 상품 조회
    Product selectOne(String id);   // 특정 상품 조회
    int insert(Product product);    // 상품 등록(등록일시 자동 입력)
    int update(Product product);    // 상품 수정
    int delete(String id);          // 상품 삭제
}
