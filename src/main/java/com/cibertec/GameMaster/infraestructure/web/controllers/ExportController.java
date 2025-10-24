package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.ExportService;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/export")
public class ExportController {

    @Autowired
    private ExportService service;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> exportOrderToPdf(@PathVariable Long orderId) {
        return service.exportInPdf(orderId);
    }



    @PostMapping("/excel")
    public ResponseEntity<?> exportOrdersToExcel(@Valid @RequestBody FilterRequest request) {
        return service.exportInExcel(request);
    }

}
