package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.OrderPort;
import com.cibertec.GameMaster.infraestructure.mapper.OrderItemMapper;
import com.cibertec.GameMaster.infraestructure.mapper.OrderMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.OrderDTO;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public OrderDTO getOrder(Long orderId){
        return orderMapper.toDTO(orderPort.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId)));
    }


}
