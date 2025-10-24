package com.cibertec.GameMaster.infraestructure.mapper;


import com.cibertec.GameMaster.domain.Report;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ExcelMapper {

    private ExcelMapper() {
    }

    public Report toModel(FilterRequest dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ExcelRequest no puede ser null");
        }

        Report report = new Report();
        report.setModule(dto.module());
        report.setFromDate(validateAndSetDate(dto.fromDate()));
        report.setToDate(validateAndSetDate(dto.toDate()));
        report.setGeneratedAt(LocalDateTime.now());
        report.setTitle("Reporte de " + (dto.module() != null ? dto.module() : "General"));
        report.setDescription(buildDescription(dto));

        return report;
    }

    private static LocalDateTime validateAndSetDate(LocalDateTime date) {
        // Puedes agregar validaciones adicionales si las necesitas
        if (date != null && date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        return date;
    }

    private static String buildDescription(FilterRequest dto) {
        StringBuilder sb = new StringBuilder();

        if (dto.fromDate() != null || dto.toDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            sb.append(" | Período: ")
                    .append(dto.fromDate() != null ? dto.fromDate().format(formatter) : "N/A")
                    .append(" - ")
                    .append(dto.toDate() != null ? dto.toDate().format(formatter) : "N/A");
        }

        return sb.toString();
    }
}
