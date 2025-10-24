package com.cibertec.GameMaster.infraestructure.database.repository;

import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o WHERE o.status = true ORDER BY o.orderDate DESC")
    List<Order> findAllOrdersDesc();

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.orderDate DESC")
    List<Order> findOrdersByCustomerId(@Param("customerId") Long customerId);

}
