package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Customer;

import java.util.Optional;

public interface CustomerPort{

    void save(Customer customer);

    Customer getCustomerByUserId(Long id);

    Optional<Customer> findById(Long id);

    boolean logicDelete(Long id);

    boolean existById(Long id);
}
