package com.cibertec.GameMaster.infraestructure.database.adapter;

import com.cibertec.GameMaster.application.port.CustomerPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Customer;
import com.cibertec.GameMaster.infraestructure.database.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerJpaAdapter implements CustomerPort {

    @Autowired
    private CustomerRepository repository;

    @Override
    public void save(Customer customer) {
        repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean logicDelete(Long id) {
        return repository.setStatusFalse(id);
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }


}
