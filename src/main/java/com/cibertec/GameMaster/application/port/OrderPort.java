package com.cibertec.GameMaster.application.port;

import com.cibertec.GameMaster.infraestructure.database.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderPort {

    void save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAllOrders();

    List<Order> findOrdersByCustomerId(Long customerId);

    List<Order> searchOrders(LocalDateTime fromDate, LocalDateTime toDate,
                                    Order.OrderStatus orderStatus,
                                    Order.PaymentMethod paymentMethod);
}
