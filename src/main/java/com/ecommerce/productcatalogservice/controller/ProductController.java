package com.ecommerce.productcatalogservice.controller;

import com.ecommerce.productcatalogservice.dto.request.ProductDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.GenericResponse;
import com.ecommerce.productcatalogservice.dto.response.ProductDtoResponse;
import com.ecommerce.productcatalogservice.mapper.ProductMapper;
import com.ecommerce.productcatalogservice.service.ProductService;
import com.ecommerce.productcatalogservice.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ProductDtoResponse>>> getAllProducts() {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.ALL_PRODUCTS_RETRIEVED_SUCCESSFULLY, productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ProductDtoResponse>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCT_RETRIEVED_SUCCESSFULLY, productService.getProductById(id)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<GenericResponse<List<ProductDtoResponse>>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCTS_BY_CATEGORY_RETRIEVED_SUCCESSFULLY, productService.getProductsByCategory(categoryId)));
    }

    @GetMapping("/price-range")
    public ResponseEntity<GenericResponse<List<ProductDtoResponse>>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCTS_BY_PRICE_RANGE_RETRIEVED_SUCCESSFULLY, productService.getProductsByPriceRange(minPrice, maxPrice)));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<ProductDtoResponse>> createProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCT_SAVED_SUCCESSFULLY, productService.saveProduct(productDtoRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ProductDtoResponse>> updateProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCT_UPDATED_SUCCESSFULLY, productService.updateProduct(productDtoRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.PRODUCT_DELETED_SUCCESSFULLY, productService.deleteProduct(id)));
    }
}
