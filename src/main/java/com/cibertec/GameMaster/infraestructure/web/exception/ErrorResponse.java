package com.cibertec.GameMaster.infraestructure.web.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        String path
) {}