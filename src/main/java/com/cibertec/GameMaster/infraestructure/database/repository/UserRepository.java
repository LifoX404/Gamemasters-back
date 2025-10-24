package com.cibertec.GameMaster.infraestructure.database.repository;

import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserEntity findByUsername(String username);
//
//    Optional<User> findByNombreUsuario(String nombreUsuario);
//
//    boolean existsByNombreUsuario(String nombreUsuario);
//
//    Optional<User> findByCodigoUsuario(UUID codigoUsuario);
}
