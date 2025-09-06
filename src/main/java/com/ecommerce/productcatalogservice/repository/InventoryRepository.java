package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    
    Optional<InventoryEntity> findByProduct_Id(Long productId);
    
    List<InventoryEntity> findByQuantityLessThanEqual(Integer quantity);
}