package com.ecommerce.productcatalogservice.controller;

import com.ecommerce.productcatalogservice.dto.request.CategoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.CategoryDtoResponse;
import com.ecommerce.productcatalogservice.dto.response.GenericResponse;
import com.ecommerce.productcatalogservice.service.CategoryService;
import com.ecommerce.productcatalogservice.utils.MessageConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<CategoryDtoResponse>>> getAllCategories() {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.ALL_CATEGORIES_RETRIEVED_SUCCESSFULLY, categoryService.getAllCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<CategoryDtoResponse>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), null, categoryService.getCategoryById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GenericResponse<CategoryDtoResponse>> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), null, categoryService.getCategoryByName(name)));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CategoryDtoResponse>> createCategory(@RequestBody CategoryDtoRequest categoryDTO) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.CATEGORY_SAVED_SUCCESSFULLY, categoryService.saveCategory(categoryDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<CategoryDtoResponse>> updateCategory(@RequestBody CategoryDtoRequest categoryDTO) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), null, categoryService.updateCategory(categoryDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.CATEGORY_DELETED_SUCCESSFULLY, categoryService.deleteCategory(id)));
    }
}
