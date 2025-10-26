package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.OrderPort;
import com.cibertec.GameMaster.application.port.ProductPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Category;
import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.database.entity.OrderItem;
import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.mapper.OrderItemMapper;
import com.cibertec.GameMaster.infraestructure.mapper.OrderMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.OrderDTO;
import com.cibertec.GameMaster.infraestructure.web.dto.cart.ItemPurchaseRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.cart.UpdateOrderRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.product.UpdateProductRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Filter;

@Service
public class OrderService {

    @Autowired
    private OrderPort orderPort;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductPort productPort;

    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderPort.findOrdersByCustomerId(customerId).stream()
                .map(order -> {
                    // Convertir los items de la orden a DTO
                    var itemDTOs = order.getOrderItems().stream()
                            .map(orderItemMapper::toDTO)
                            .toList();

                    // Mapear la orden a DTO
                    var orderDTO = orderMapper.toDTO(order);

                    // Asignar los items mapeados al DTO
                    orderDTO.setOrderItems(itemDTOs);

                    return orderDTO;
                })
                .toList();
    }

    public List<OrderDTO> getAllOrders() {
        return orderPort.findAllOrders().stream()
                .map(order -> {
                    // Convertir los items de la orden a DTO
                    var itemDTOs = order.getOrderItems().stream()
                            .map(orderItemMapper::toDTO)
                            .toList();

                    // Mapear la orden a DTO
                    var orderDTO = orderMapper.toDTO(order);

                    // Asignar los items mapeados al DTO
                    orderDTO.setOrderItems(itemDTOs);

                    return orderDTO;
                })
                .toList();
    }

    public OrderDTO getOrder(Long orderId){
        return orderMapper.toDTO(orderPort.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId)));
    }

    public List<OrderDTO> getOrdersByFilter(FilterRequest request) {
        return orderPort.searchOrders(request.fromDate(),
                request.toDate(),
                request.orderStatus(),
                request.paymentMethod()
                        ).stream()
                .map(order -> {
                    // Convertir los items de la orden a DTO
                    var itemDTOs = order.getOrderItems().stream()
                            .map(orderItemMapper::toDTO)
                            .toList();

                    // Mapear la orden a DTO
                    var orderDTO = orderMapper.toDTO(order);

                    // Asignar los items mapeados al DTO
                    orderDTO.setOrderItems(itemDTOs);

                    return orderDTO;
                })
                .toList();
    }

    public void updateOrder(Long id, UpdateOrderRequest request) {
        Order entity = orderPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        // Actualiza TODOS los campos, incluso si son null
        entity.setOrderStatus(request.orderStatus());
        entity.setDeliveryAddress(request.deliveryAddress());
        entity.setDeliveryPhone(request.deliveryPhone());
        entity.setPaymentMethod(request.paymentMethod());
        entity.setObservations(request.observations());

        // Los items SIEMPRE se actualizan en PUT
        entity.getOrderItems().clear();

        BigDecimal newTotal = BigDecimal.ZERO;

        if (request.items() != null && !request.items().isEmpty()) {
            for (ItemPurchaseRequest itemRequest : request.items()) {
                Product product = productPort.findById(itemRequest.productId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.productId()));

                BigDecimal subtotal = product.getPrice()
                        .multiply(BigDecimal.valueOf(itemRequest.itemCount()));

                OrderItem orderItem = OrderItem.builder()
                        .order(entity)
                        .productId(product.getId())
                        .productName(product.getName())
                        .itemQuantity(itemRequest.itemCount())
                        .unitPrice(product.getPrice())
                        .subtotal(subtotal)
                        .build();

                entity.getOrderItems().add(orderItem);
                newTotal = newTotal.add(subtotal);
            }
        }

        entity.setOrderTotal(newTotal);
        orderPort.save(entity);
    }


}
