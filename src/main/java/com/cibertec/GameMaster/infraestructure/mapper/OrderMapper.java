package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.web.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);
}
