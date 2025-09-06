package com.ecommerce.productcatalogservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDtoResponse {
    private Long id;
    private Long productId;
    private String productTitle;
    private Integer quantity;
    private Integer lowStockThreshold;
}