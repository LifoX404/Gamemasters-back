package com.cibertec.GameMaster.infraestructure.web.dto.cart;

import com.cibertec.GameMaster.infraestructure.database.entity.Order;

import java.util.List;

public record UpdateOrderRequest(

        Order.OrderStatus orderStatus,
        String deliveryAddress,
        String deliveryPhone,
        Order.PaymentMethod paymentMethod,
        String observations,
        List<ItemPurchaseRequest> items
) {
}
