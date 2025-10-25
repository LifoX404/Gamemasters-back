package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.OrderItem;
import com.cibertec.GameMaster.infraestructure.web.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDTO toDTO(OrderItem order);

    OrderItem toEntity(OrderItemDTO orderDTO);
}
