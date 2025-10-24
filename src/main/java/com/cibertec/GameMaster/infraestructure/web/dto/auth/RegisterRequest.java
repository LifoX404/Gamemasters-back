package com.cibertec.GameMaster.infraestructure.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank(message = "El usuario es obligatorio")
        String username,
        @NotBlank(message = "La contraseña es obligatorio")
        String password,
        @NotBlank
        @Email(message = "El correo electrónico no es válido")
        String email,

        @NotBlank(message = "El nombre es obligatorio")
        String firstName,
        String lastName,
        String phone,
        String address
) {
}
