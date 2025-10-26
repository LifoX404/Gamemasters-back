package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerPort{

    List<Customer> findAll();

    List<Customer> findAllActive();

    void save(Customer customer);

    Customer getCustomerByUserId(Long id);

    Optional<Customer> findById(Long id);

    void logicDelete(Long id);

    boolean existById(Long id);
}
