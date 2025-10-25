package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.Customer;
import com.cibertec.GameMaster.infraestructure.web.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer user);

    Customer toEntity(CustomerDTO userDTO);
}
