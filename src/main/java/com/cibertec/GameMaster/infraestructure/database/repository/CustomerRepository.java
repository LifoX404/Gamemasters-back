package com.cibertec.GameMaster.infraestructure.database.repository;

import com.cibertec.GameMaster.infraestructure.database.entity.Customer;
import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    Optional<Customer> findByUsuario(User user);
//
//    Optional<Customer> findByCodigoCliente(UUID codigoCliente);
//


    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.status = false WHERE c.id = :id")
    boolean setStatusFalse(@Param("id") Long id);

    Customer findByUser(UserEntity user);



}
