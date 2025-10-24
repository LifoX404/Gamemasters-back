package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.CartService;
import com.cibertec.GameMaster.infraestructure.web.dto.ApiResponse;
import com.cibertec.GameMaster.infraestructure.web.dto.cart.PurchaseRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/purchase")
    public ResponseEntity<?> process(@Valid @RequestBody PurchaseRequest request) {
        Long response = cartService.procesarOrden(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Orden Procesada Correctamente")
                        .data(response)
                        .build()
        );
    }

}
