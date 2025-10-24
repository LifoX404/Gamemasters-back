package com.cibertec.GameMaster.infraestructure.database.adapter;

import com.cibertec.GameMaster.application.port.ProductPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductJpaAdapter implements ProductPort {

    @Autowired
    private ProductRepository repository;

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> getProducts() {
        return repository.findAllActive();
    }

    @Override
    public void logicDelete(Long productId) {
        repository.setStatusFalse(productId);
    }
}
