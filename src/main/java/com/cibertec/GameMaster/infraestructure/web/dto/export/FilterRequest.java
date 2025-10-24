package com.cibertec.GameMaster.infraestructure.web.dto.export;

import com.cibertec.GameMaster.infraestructure.database.entity.Order.OrderStatus;
import com.cibertec.GameMaster.infraestructure.database.entity.Order.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record FilterRequest(

        @NotBlank(message = "El módulo no puede estar vacío")
        @Size(max = 50, message = "El módulo no puede exceder los 50 caracteres")
        String module,

        PaymentMethod paymentMethod,

        OrderStatus orderStatus,

        @PastOrPresent(message = "La fecha inicial no puede ser futura")
        LocalDateTime fromDate,

        @PastOrPresent(message = "La fecha final no puede ser futura")
        LocalDateTime toDate

) {
    public FilterRequest {
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("La fecha inicial debe ser anterior a la fecha final");
        }
    }
}
