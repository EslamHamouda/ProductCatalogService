package com.ecommerce.productcatalogservice.controller;

import com.ecommerce.productcatalogservice.dto.request.InventoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.GenericResponse;
import com.ecommerce.productcatalogservice.dto.response.InventoryDtoResponse;
import com.ecommerce.productcatalogservice.mapper.InventoryMapper;
import com.ecommerce.productcatalogservice.service.InventoryService;
import com.ecommerce.productcatalogservice.service.ProductService;
import com.ecommerce.productcatalogservice.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductService productService;
    private final InventoryMapper inventoryMapper;

    @Autowired
    public InventoryController(InventoryService inventoryService, ProductService productService, InventoryMapper inventoryMapper) {
        this.inventoryService = inventoryService;
        this.productService = productService;
        this.inventoryMapper = inventoryMapper;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<InventoryDtoResponse>>> getAllInventory() {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.ALL_INVENTORY_RETRIEVED_SUCCESSFULLY, inventoryService.getAllInventory()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<InventoryDtoResponse>> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_RETRIEVED_SUCCESSFULLY, inventoryService.getInventoryById(id)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<GenericResponse<InventoryDtoResponse>> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_RETRIEVED_SUCCESSFULLY, inventoryService.getInventoryByProductId(productId)));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<GenericResponse<List<InventoryDtoResponse>>> getLowStockInventory(@RequestParam(defaultValue = "5") Integer threshold) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.LOW_STOCK_INVENTORY_RETRIEVED_SUCCESSFULLY, inventoryService.getLowStockInventory(threshold)));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<InventoryDtoResponse>> createInventory(@RequestBody InventoryDtoRequest inventoryDtoRequest) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_SAVED_SUCCESSFULLY, inventoryService.saveInventory(inventoryDtoRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<InventoryDtoResponse>> updateInventory(@PathVariable Long id, @RequestBody InventoryDtoRequest inventoryDtoRequest) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_UPDATED_SUCCESSFULLY, inventoryService.updateInventory(inventoryDtoRequest)));
    }

    @PatchMapping("/update-stock/{productId}")
    public ResponseEntity<GenericResponse<InventoryDtoResponse>> updateStock(
            @PathVariable Long productId,
            @RequestParam Integer quantityChange) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_STOCK_UPDATED_SUCCESSFULLY, inventoryService.updateInventoryStock(productId, quantityChange)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteInventory(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponse<>(OK.value(), MessageConstants.INVENTORY_DELETED_SUCCESSFULLY, inventoryService.deleteInventory(id)));
    }
}
