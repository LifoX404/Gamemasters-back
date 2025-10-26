package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.CustomerService;
import com.cibertec.GameMaster.infraestructure.web.dto.ApiResponse;
import com.cibertec.GameMaster.infraestructure.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getCustomers() {
        List<CustomerDTO> response = customerService.getCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Obtenido Correctamente")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id
    ) {
        CustomerDTO response = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Obtenido Correctamente")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id
    ) {customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Obtenido Correctamente")
                        .data(true)
                        .build()
        );
    }


}
