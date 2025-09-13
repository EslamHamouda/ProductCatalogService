package com.ecommerce.productcatalogservice.mapper;

import com.ecommerce.productcatalogservice.dto.request.CategoryDtoRequest;
import com.ecommerce.productcatalogservice.dto.response.CategoryDtoResponse;
import com.ecommerce.productcatalogservice.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "productIds", source = "product")
    CategoryDtoResponse toCategoryResponse(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    CategoryEntity toCategoryEntity(CategoryDtoRequest categoryDtoRequest);

    default Long mapProductToId(com.ecommerce.productcatalogservice.entity.ProductEntity productEntity) {
        return productEntity.getId();
    }
}