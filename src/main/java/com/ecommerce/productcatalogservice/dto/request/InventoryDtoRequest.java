package com.ecommerce.productcatalogservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDtoRequest {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer lowStockThreshold;
}