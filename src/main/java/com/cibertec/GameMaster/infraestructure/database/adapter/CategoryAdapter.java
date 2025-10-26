package com.cibertec.GameMaster.infraestructure.database.adapter;

import com.cibertec.GameMaster.application.port.CategoryPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Category;
import com.cibertec.GameMaster.infraestructure.database.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryAdapter implements CategoryPort {

    @Autowired
    private CategoryRepository repository;


    @Override
    public void save(Category customer) {
        repository.save(customer);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
}
