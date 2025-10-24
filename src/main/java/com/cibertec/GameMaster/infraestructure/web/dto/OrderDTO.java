package com.cibertec.GameMaster.infraestructure.web.dto;

import com.cibertec.GameMaster.infraestructure.database.entity.Order.OrderStatus;
import com.cibertec.GameMaster.infraestructure.database.entity.Order.PaymentMethod;
import com.cibertec.GameMaster.infraestructure.database.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDTO {


    private Long id;

    private LocalDateTime orderDate;

    private BigDecimal orderTotal;

    private OrderStatus orderStatus;

    private String deliveryAddress;

    private String deliveryPhone;

    private PaymentMethod paymentMethod;

    private String observations;

    private Boolean status;

    private List<OrderItemDTO> orderItems;
}
