package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserPort {

    List<UserEntity> getAllUsers();

    List<UserEntity> getUsersActive();

    Optional<UserEntity> getUserById(Long id);

    Optional<UserEntity> getUserByUsername(String username);

    boolean logicDelete(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(UserEntity userEntity);
}
