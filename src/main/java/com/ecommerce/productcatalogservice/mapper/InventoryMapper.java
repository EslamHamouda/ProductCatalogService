package com.ecommerce.productcatalogservice.mapper;

import com.ecommerce.productcatalogservice.dto.request.InventoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.InventoryDtoResponse;
import com.ecommerce.productcatalogservice.entity.InventoryEntity;
import com.ecommerce.productcatalogservice.entity.ProductEntity;
import com.ecommerce.productcatalogservice.repository.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    InventoryDtoResponse toInventoryResponse(InventoryEntity inventoryEntity);

    @Mapping(target = "product", ignore = true)
    InventoryEntity toInventoryEntity(InventoryDtoRequest inventoryDtoRequest);
}
