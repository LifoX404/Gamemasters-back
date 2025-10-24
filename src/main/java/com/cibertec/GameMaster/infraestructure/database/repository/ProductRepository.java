package com.cibertec.GameMaster.infraestructure.database.repository;

import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status = false WHERE p.id = :productId")
    void setStatusFalse(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.status = true")
    List<Product> findAllActive();

}
