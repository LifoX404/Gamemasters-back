package com.cibertec.GameMaster.infraestructure.mapper;

import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import com.cibertec.GameMaster.infraestructure.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(UserEntity user);

    UserEntity toEntity(UserDTO userDTO);
}
