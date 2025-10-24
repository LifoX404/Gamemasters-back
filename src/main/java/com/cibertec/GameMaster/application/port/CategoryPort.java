package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Category;

import java.util.Optional;

public interface CategoryPort {

    void save(Category customer);

    Optional<Category> findById(Long id);

//    boolean logicDelete(UUID id);

    boolean existById(Long id);
}
