package com.cibertec.GameMaster.infraestructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;

    private Long productId;

    private String productName;

    private int itemQuantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

    private boolean status;
}
