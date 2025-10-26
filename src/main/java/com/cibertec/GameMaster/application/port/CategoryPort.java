package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Category;
import com.cibertec.GameMaster.infraestructure.web.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;


public interface CategoryPort {

    void save(Category customer);

    Optional<Category> findById(Long id);

    List<Category> findAll();

//    boolean logicDelete(UUID id);

    boolean existById(Long id);
}
