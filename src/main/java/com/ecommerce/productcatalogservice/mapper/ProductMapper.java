package com.ecommerce.productcatalogservice.mapper;

import com.ecommerce.productcatalogservice.dto.request.ProductDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.ProductDtoResponse;
import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import com.ecommerce.productcatalogservice.entity.InventoryEntity;
import com.ecommerce.productcatalogservice.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "category", target = "categoryId", qualifiedByName = "categoryToCategoryId")
    @Mapping(source = "category", target = "categoryName", qualifiedByName = "categoryToCategoryName")
    @Mapping(source = "inventory", target = "inventoryQuantity", qualifiedByName = "inventoryToQuantity")
    @Mapping(source = "inventory", target = "lowStockThreshold", qualifiedByName = "inventoryToLowStockThreshold")
    ProductDtoResponse toProductResponse(ProductEntity productEntity);

    @Named("categoryToCategoryId")
    default Long categoryEntityToCategoryId(CategoryEntity categoryEntity) {
        return categoryEntity != null ? categoryEntity.getId() : null;
    }

    @Named("categoryToCategoryName")
    default String categoryEntityToCategoryName(CategoryEntity categoryEntity) {
        return categoryEntity != null ? categoryEntity.getName() : null;
    }

    @Named("inventoryToQuantity")
    default Integer inventoryEntityToQuantity(InventoryEntity inventoryEntity) {
        return inventoryEntity != null ? inventoryEntity.getQuantity() : null;
    }

    @Named("inventoryToLowStockThreshold")
    default Integer inventoryEntityToLowStockThreshold(InventoryEntity inventoryEntity) {
        return inventoryEntity != null ? inventoryEntity.getLowStockThreshold() : null;
    }
}
