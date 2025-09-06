package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.entity.ProductEntity;
import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
    
    List<ProductEntity> findByTitleContainingIgnoreCase(String title);
    
    List<ProductEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<ProductEntity> findByCategoryAndPriceBetween(CategoryEntity categoryEntity, BigDecimal minPrice, BigDecimal maxPrice);
}