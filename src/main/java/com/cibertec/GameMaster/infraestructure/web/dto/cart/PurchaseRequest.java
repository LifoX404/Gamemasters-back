package com.cibertec.GameMaster.infraestructure.web.dto.cart;

import com.cibertec.GameMaster.infraestructure.database.entity.Order.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PurchaseRequest(

        @NotNull(message = "El ID del cliente no puede ser nulo")
        Long customerId,
        @NotBlank
        String address,
        PaymentMethod paymentMethod,
        String observations,
        @NotBlank
        String deliveryAddress,
        @NotBlank
        @Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 dígitos")
        String deliveryPhone,

        @NotNull
        @Size(message = "Para procesar la compra debe agregar al menos un producto")
        List<ItemPurchaseRequest> items

){
}
