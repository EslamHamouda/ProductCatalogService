package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @EntityGraph(attributePaths = "productEntities")
    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findByName(String name);
    
    boolean existsByName(String name);
}