package com.cibertec.GameMaster.infraestructure.web.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

public record ItemPurchaseRequest(
        @NonNull
        Long productId,

        @NonNull
        @Min(1)
        Integer itemCount
){
}
