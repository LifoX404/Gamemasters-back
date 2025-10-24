package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.web.dto.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDTO(Product user);

    Product toEntity(ProductDto userDTO);
}