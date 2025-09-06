package com.ecommerce.productcatalogservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDtoResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> productIds = new ArrayList<>();
}