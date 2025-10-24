package com.cibertec.GameMaster.infraestructure.web.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private String details;

    private BigDecimal price;

    private int stock;

    private String imgUrl;

    private Boolean status;

}
