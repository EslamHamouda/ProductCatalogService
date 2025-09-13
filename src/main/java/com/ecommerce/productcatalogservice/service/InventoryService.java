package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.request.InventoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.InventoryDtoResponse;
import com.ecommerce.productcatalogservice.entity.InventoryEntity;
import com.ecommerce.productcatalogservice.entity.ProductEntity;
import com.ecommerce.productcatalogservice.exception.ResourceNotFoundException;
import com.ecommerce.productcatalogservice.exception.DuplicateResourceException;
import com.ecommerce.productcatalogservice.mapper.InventoryMapper;
import com.ecommerce.productcatalogservice.repository.InventoryRepository;
import com.ecommerce.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.inventoryMapper = inventoryMapper;
    }

    public List<InventoryDtoResponse> getAllInventory() {
        List<InventoryEntity> inventoryEntities = inventoryRepository.findAll();
        return inventoryEntities.stream()
                .map(inventoryMapper::toInventoryResponse)
                .collect(Collectors.toList());
    }

    public InventoryDtoResponse getInventoryById(Long id) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findById(id);
        return inventoryMapper.toInventoryResponse(inventoryEntity.orElseThrow(() -> 
                new ResourceNotFoundException("Inventory not found with id: " + id)));
    }

    public InventoryDtoResponse getInventoryByProductId(Long productId) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findByProduct_Id(productId);
        return inventoryMapper.toInventoryResponse(inventoryEntity.orElseThrow(() -> 
                new ResourceNotFoundException("Inventory not found for product id: " + productId)));
    }

    public List<InventoryDtoResponse> getLowStockInventory(Integer threshold) {
        List<InventoryEntity> inventoryEntities = inventoryRepository.findByQuantityLessThanEqual(threshold);
        return inventoryEntities.stream()
                .map(inventoryMapper::toInventoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryDtoResponse saveInventory(InventoryDtoRequest inventoryDtoRequest) {
        // Check if product exists
        ProductEntity productEntity = productRepository.findById(inventoryDtoRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + inventoryDtoRequest.getProductId()));

        // Check if inventory for this product already exists
        if (inventoryRepository.findByProduct_Id(inventoryDtoRequest.getProductId()).isPresent()) {
            throw new DuplicateResourceException("Inventory already exists for product id: " + inventoryDtoRequest.getProductId());
        }

        // Create new inventory entity - the mapper will handle setting the product
        InventoryEntity inventoryEntity = inventoryMapper.toInventoryEntity(inventoryDtoRequest);
        inventoryEntity.setProduct(productEntity);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventoryEntity));
    }

    @Transactional
    public InventoryDtoResponse updateInventory(InventoryDtoRequest inventoryDtoRequest) {
        // Check if inventory exists
        InventoryEntity existingInventory = inventoryRepository.findById(inventoryDtoRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + inventoryDtoRequest.getId()));

        // Update the inventory entity - only update fields that are provided
        if(inventoryDtoRequest.getQuantity() != null){
            existingInventory.setQuantity(inventoryDtoRequest.getQuantity());
        }
        if(inventoryDtoRequest.getLowStockThreshold() != null){
            existingInventory.setLowStockThreshold(inventoryDtoRequest.getLowStockThreshold());
        }

        // Update product if it's being changed
        if (!existingInventory.getProduct().getId().equals(inventoryDtoRequest.getProductId())) {
            ProductEntity productEntity = productRepository.findById(inventoryDtoRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + inventoryDtoRequest.getProductId()));
            existingInventory.setProduct(productEntity);
        }
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(existingInventory));
    }

    @Transactional
    public InventoryDtoResponse updateInventoryStock(Long productId, Integer quantityChange) {
        InventoryEntity inventoryEntity = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product id: " + productId));

        int newQuantity = inventoryEntity.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Cannot reduce stock below zero");
        }
        inventoryEntity.setQuantity(newQuantity);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventoryEntity));
    }

    @Transactional
    public String deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
        return null;
    }
}
