package com.cibertec.GameMaster.infraestructure.web.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String description,
        String details,
        @NotBlank(message = "La categoria es obligatoria")
        Long categoryId,
        @Min(value = 0, message = "El precio no puede ser negativo")
        BigDecimal price,
        @NotBlank
        @Min(value = 0,message = "El stock no puede ser negativo")
        int stock,
        String imgUrl
) {

    public UpdateProductRequest {
        if (imgUrl == null ) imgUrl = "https://i.pinimg.com/736x/7e/1b/2f/7e1b2f9807ab37bec9ff438070b97be9.jpg";
    }
}
