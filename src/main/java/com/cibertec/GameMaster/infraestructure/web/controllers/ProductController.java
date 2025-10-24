package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.ProductService;
import com.cibertec.GameMaster.infraestructure.web.dto.ApiResponse;
import com.cibertec.GameMaster.infraestructure.web.dto.product.ProductDto;
import com.cibertec.GameMaster.infraestructure.web.dto.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts() {
        List<ProductDto> response = productService.getProducts();
        return ResponseEntity.status(HttpStatus.FOUND).body(
                ApiResponse.builder()
                        .message("Orden Procesada Correctamente")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        ProductDto response = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(
                ApiResponse.builder()
                        .message("Orden Procesada Correctamente")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody UpdateProductRequest request) {
        ProductDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Orden Procesada Correctamente")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        productService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Producto "+id+ " actualizado correctamente")
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.builder()
                        .message("Producto "+id+ " eliminado correctamente")
                        .data(null)
                        .build()
        );
    }
}
