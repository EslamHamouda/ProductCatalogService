package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.request.CategoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.CategoryDtoResponse;
import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import com.ecommerce.productcatalogservice.exception.DuplicateResourceException;
import com.ecommerce.productcatalogservice.exception.ResourceNotFoundException;
import com.ecommerce.productcatalogservice.mapper.CategoryMapper;
import com.ecommerce.productcatalogservice.repository.CategoryRepository;
import com.ecommerce.productcatalogservice.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDtoResponse> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryDtoResponse getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        return categoryMapper.toCategoryResponse(categoryEntity.orElseThrow(()->new ResourceNotFoundException("Category not found")));
    }

    public CategoryDtoResponse getCategoryByName(String name) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(name);
        return categoryMapper.toCategoryResponse(categoryEntity.orElseThrow(()->new ResourceNotFoundException("Category not found")));
    }

    @Transactional
    public CategoryDtoResponse saveCategory(CategoryDtoRequest categoryDtoRequest) {
        if (categoryRepository.existsByName(categoryDtoRequest.getName())) {
            throw new DuplicateResourceException("Category with name " + categoryDtoRequest.getName() + " already exists");
        }
        return categoryMapper.toCategoryResponse(categoryRepository.save(categoryMapper.toCategoryEntity(categoryDtoRequest)));
    }

    @Transactional
    public CategoryDtoResponse updateCategory(CategoryDtoRequest categoryDtoRequest) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findByName(categoryDtoRequest.getName());
        if (existingCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category with name " + categoryDtoRequest.getName() + " does not exist");
        }

        if (!existingCategory.get().getName().equals(categoryDtoRequest.getName()) &&
                categoryRepository.existsByName(categoryDtoRequest.getName())) {
            throw new ResourceNotFoundException("Category with name '" + categoryDtoRequest.getName() + "' already exists");
        }
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(categoryDtoRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(categoryEntity));
    }

    @Transactional
    public String deleteCategory(Long id) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isEmpty()) {
           throw new ResourceNotFoundException("Category with id " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
        return null;
    }
}