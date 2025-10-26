package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.web.dto.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId") // Mapear ID de la categoría
    @Mapping(source = "category.name", target = "categoryName") // Mapear nombre de la categoría
    ProductDto toDTO(Product product);

    @Mapping(source = "categoryId", target = "category.id") // Mapear categoryId a la entidad Product
    @Mapping(source = "categoryName", target = "category.name") // Mapear categoryName a la entidad Product
    Product toEntity(ProductDto productDto);
}
