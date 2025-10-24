package com.cibertec.GameMaster.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
                String message,
                T data,
                Map<String, Object> meta
){

    public ApiResponse(String message, T data) {
        this(message, data, null);
    }

    public ApiResponse(String message) {
        this(message, null, null);
    }
}
