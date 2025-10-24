package com.cibertec.GameMaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private String module;           // "Ventas", "Usuarios", etc.
    private LocalDateTime fromDate;      // Puede ser null
    private LocalDateTime toDate;        // Puede ser null// A quién se envía     // Stored procedure o query base// Tipo de base de datos (Oracle, SQL Server, etc.)
    private LocalDateTime generatedAt; // Fecha/hora en que se genera el reporte     // Opcional: usuario que lo pidió
    private String title;            // Ej: "Reporte de ventas del Q3"
    private String description;      // Opcional: descripción larga


    public String getFormattedPeriod() {
        if (fromDate == null && toDate == null) return null;
        String desde = fromDate != null ? fromDate.toString() : "N/A";
        String hasta = toDate != null ? toDate.toString() : "N/A";
        return "Período: " + desde + " - " + hasta;
    }

    public String getFormattedFileName() {
        String formattedDate = generatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return String.format("Reporte-%s-%s.xlsx", module, formattedDate);
    }
}
