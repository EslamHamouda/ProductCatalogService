package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.request.ProductDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.ProductDtoResponse;
import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import com.ecommerce.productcatalogservice.entity.ProductEntity;
import com.ecommerce.productcatalogservice.exception.ResourceNotFoundException;
import com.ecommerce.productcatalogservice.mapper.ProductMapper;
import com.ecommerce.productcatalogservice.repository.CategoryRepository;
import com.ecommerce.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDtoResponse> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductDtoResponse getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toProductResponse(productEntity);
    }

    public List<ProductDtoResponse> getProductsByCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        List<ProductEntity> productEntities = productRepository.findByCategory(categoryEntity);
        return productEntities.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductDtoResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<ProductEntity> productEntities = productRepository.findByPriceBetween(minPrice, maxPrice);
        return productEntities.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDtoResponse saveProduct(ProductDtoRequest productDtoRequest) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDtoRequest.getName());
        productEntity.setDescription(productDtoRequest.getDescription());
        productEntity.setPrice(productDtoRequest.getPrice());

        if (productDtoRequest.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(productDtoRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDtoRequest.getCategoryId()));
            productEntity.setCategory(categoryEntity);
        }

        return productMapper.toProductResponse(productRepository.save(productEntity));
    }

    @Transactional
    public ProductDtoResponse updateProduct(ProductDtoRequest productDtoRequest) {
        ProductEntity existingProduct = productRepository.findById(productDtoRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productDtoRequest.getId()));

        existingProduct.setName(productDtoRequest.getName());
        existingProduct.setDescription(productDtoRequest.getDescription());
        existingProduct.setPrice(productDtoRequest.getPrice());

        if (productDtoRequest.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(productDtoRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDtoRequest.getCategoryId()));
            existingProduct.setCategory(categoryEntity);
        } else {
            existingProduct.setCategory(null);
        }
        return productMapper.toProductResponse(productRepository.save(existingProduct));
    }

    @Transactional
    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return null;
    }
}
