package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.OrderService;
import com.cibertec.GameMaster.infraestructure.web.dto.ApiResponse;
import com.cibertec.GameMaster.infraestructure.web.dto.OrderDTO;
import com.cibertec.GameMaster.infraestructure.web.dto.cart.UpdateOrderRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/get")
    public ResponseEntity<?> getOrders() {
        List<OrderDTO> orders = service.getAllOrders();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Ordenes listadas Correctamente")
                        .data(orders)
                        .build()
        );
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        OrderDTO order = service.getOrder(orderId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Ordenes listadas Correctamente")
                        .data(order)
                        .build()
        );
    }

    @GetMapping("/from/{id}")
    public ResponseEntity<?> getOrdersByCustomer(@PathVariable Long id) {
        List<OrderDTO> orders = service.getOrdersByCustomerId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Ordenes listadas Correctamente")
                        .data(orders)
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> getOrdersByFilter(@ModelAttribute FilterRequest request){
        List<OrderDTO> orderDTOs = service.getOrdersByFilter(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Ordenes listadas Correctamente")
                        .data(orderDTOs)
                        .build()
        );
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateOrderRequest request) {
        service.updateOrder(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Producto "+id+ " actualizado correctamente")
                        .data(null)
                        .build()
        );
    }

}
