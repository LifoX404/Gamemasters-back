package com.cibertec.GameMaster.infraestructure.database.adapter;

import com.cibertec.GameMaster.application.port.OrderPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.database.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderJpaAdapter implements OrderPort {

    @Autowired
    private OrderRepository repository;

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAllOrders() {
        return repository.findAllOrdersDesc();
    }

    @Override
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return repository.findOrdersByCustomerId(customerId);
    }

    // FILTROS


    private Specification<Order> hasDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            if (fromDate != null && toDate != null) {
                return cb.between(root.get("orderDate"), fromDate, toDate);
            }
            return null;
        };
    }

    private Specification<Order> hasOrderStatus(Order.OrderStatus orderStatus) {
        return (root, query, cb) -> {
            if (orderStatus != null) {
                return cb.equal(root.get("orderStatus"), orderStatus);
            }
            return null;
        };
    }

    private Specification<Order> hasPaymentMethod(Order.PaymentMethod paymentMethod) {
        return (root, query, cb) -> {
            if (paymentMethod != null) {
                return cb.equal(root.get("paymentMethod"), paymentMethod);
            }
            return null;
        };
    }

    private Specification<Order> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), true);
    }

    @Override
    public List<Order> searchOrders(LocalDateTime fromDate, LocalDateTime toDate,
                                    Order.OrderStatus orderStatus,
                                    Order.PaymentMethod paymentMethod) {
        List<Specification<Order>> specs = new ArrayList<>();

        if (fromDate != null && toDate != null) {
            specs.add(hasDateRange(fromDate, toDate));
        }
        if (orderStatus != null) {
            specs.add(hasOrderStatus(orderStatus));
        }
        if (paymentMethod != null) {
            specs.add(hasPaymentMethod(paymentMethod));
        }
        specs.add(isActive());

        Specification<Order> finalSpec = Specification.allOf(specs);

        return repository.findAll(finalSpec);
    }

}
