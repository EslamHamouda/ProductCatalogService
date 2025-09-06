package com.ecommerce.productcatalogservice.mapper;

import com.ecommerce.productcatalogservice.dto.request.InventoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.InventoryDtoResponse;
import com.ecommerce.productcatalogservice.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {
    InventoryDtoResponse toInventoryResponse(InventoryEntity inventoryEntity);
    InventoryEntity toInventoryEntity(InventoryDtoRequest inventoryDtoRequest);
}
