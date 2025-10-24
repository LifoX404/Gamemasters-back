package com.cibertec.GameMaster.infraestructure.web.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cibertec.GameMaster.infraestructure.web.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            WebRequest request) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .message("Error")
                        .data(ex.getMessage()
                        ).build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {

        String errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(
                errors,
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
    }

    // ==================== SEGURIDAD ====================

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            WebRequest request) {
        return buildErrorResponse(
                "Credenciales inválidas",
                HttpStatus.UNAUTHORIZED,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            WebRequest request) {
        return buildErrorResponse(
                "Error de autenticación: " + ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            WebRequest request) {
        return buildErrorResponse(
                "Acceso denegado: No tienes permisos para acceder a este recurso",
                HttpStatus.FORBIDDEN,
                request.getDescription(false)
        );
    }

    // ==================== ERRORES DE TIPOS ====================

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        String message = String.format(
                "El parámetro '%s' debe ser de tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"
        );

        return buildErrorResponse(
                message,
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
    }

    // ==================== CATCH-ALL ====================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        // Log del error para debugging (en producción usa un logger real)
        System.err.println("Error no manejado: " + ex.getClass().getName());
        ex.printStackTrace();

        return buildErrorResponse(
                "Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getDescription(false)
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            String message,
            HttpStatus status,
            String path) {

        ErrorResponse error = new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now(),
                path.replace("uri=", "")
        );

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ValidationErrorResponse> handleUnexpectedTypeException(
            UnexpectedTypeException ex,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        errors.put("tipoInvalido", ex.getMessage());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de tipo de validación",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ValidationErrorResponse> handleJWTVerificationException(
            JWTVerificationException ex,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        errors.put("token", "Token inválido o no autorizado");

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación JWT",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Recurso no encontrado");
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
