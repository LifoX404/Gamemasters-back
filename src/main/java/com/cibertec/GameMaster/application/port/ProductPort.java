package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductPort {

    boolean existsById(Long id);

    void save(Product product);

    Optional<Product> findById(Long id);

    List<Product> getProducts();

    void logicDelete(Long productId);
}
