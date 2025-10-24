package com.cibertec.GameMaster.utils;


import com.cibertec.GameMaster.domain.Report;
import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RenderExcelUtil{

    public static File generate(Stream<Map<String, Object>> data, Report report) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet(report.getModule());

        AtomicInteger rowIndex = new AtomicInteger(0);

        try (Stream<Map<String, Object>> streamToProcess = data) {
            Iterator<Map<String, Object>> iterator = streamToProcess.iterator();

            if (iterator.hasNext()) {
                Map<String, Object> firstRow = iterator.next();
                List<String> columns = new ArrayList<>(firstRow.keySet());

                // Aplica encabezados y metadatos del Report
                applyHeaderAndTitleRows(workbook, sheet, rowIndex, columns, report);

                writeDataRows(iterator, sheet, rowIndex, columns, firstRow);
                setFixedColumnWidths(sheet, columns.size());
            }
        }

        return saveWorkbook(workbook);
    }

    private static void applyHeaderAndTitleRows(
            SXSSFWorkbook workbook,
            Sheet sheet,
            AtomicInteger rowIndex,
            List<String> columns,
            Report report) {

        int numColumns = columns.size();

        // Crear estilos
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle periodStyle = createPeriodStyle(workbook);

        // Cabeceras
        sheet.createRow(rowIndex.getAndIncrement());

        String modulo = report.getModule() != null ? report.getModule() : "General";
        createMergedRow(sheet, rowIndex.getAndIncrement(), "Reporte de " + modulo, titleStyle, numColumns);

        String period = report.getFormattedPeriod();
        if (period != null) {
            createMergedRow(sheet, rowIndex.getAndIncrement(), period, periodStyle, numColumns);
        }

        sheet.createRow(rowIndex.getAndIncrement());

        createHeaderRow(sheet, rowIndex.getAndIncrement(), columns, headerStyle);

    }

    private static CellStyle createHeaderStyle(SXSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);

        try {
            XSSFColor redColor = new XSSFColor(new java.awt.Color(12, 44, 89), null);
            ((XSSFCellStyle) style).setFillForegroundColor(redColor);
        } catch (Exception e) {
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
        }

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private static CellStyle createTitleStyle(SXSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private static CellStyle createPeriodStyle(SXSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);

        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private static void createHeaderRow(Sheet sheet, int rowNum, List<String> columns, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        int cellIndex = 0;
        for (String colName : columns) {
            Cell cell = row.createCell(cellIndex++);
            cell.setCellValue(colName);
            cell.setCellStyle(style);
        }
    }

    private static void createMergedRow(Sheet sheet, int rowNum, String text, CellStyle style, int numColumns) {
        Row row = sheet.createRow(rowNum);
        Cell cell = row.createCell(0);
        cell.setCellValue(text);
        cell.setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, numColumns - 1));
    }

    private static void writeDataRows(
            Iterator<Map<String, Object>> iterator,
            Sheet sheet,
            AtomicInteger rowIndex,
            List<String> columns,
            Map<String, Object> firstRow) {

        // Primera fila de datos
        Row excelRow = sheet.createRow(rowIndex.getAndIncrement());
        int cellIndex = 0;
        for (String col : columns) {
            Object value = firstRow.get(col);
            excelRow.createCell(cellIndex++).setCellValue(value != null ? value.toString() : "");
        }

        // Resto de filas
        iterator.forEachRemaining(row -> {
            Row rowExcel = sheet.createRow(rowIndex.getAndIncrement());
            int idx = 0;
            for (String col : columns) {
                Object value = row.get(col);
                rowExcel.createCell(idx++).setCellValue(value != null ? value.toString() : "");
            }
        });
    }

    private static void setFixedColumnWidths(Sheet sheet, int numColumns) {
        int defaultWidth = 4000;

        for (int i = 0; i < numColumns; i++) {
            sheet.setColumnWidth(i, defaultWidth);
        }
    }

    private static File saveWorkbook(SXSSFWorkbook workbook) {
        try {
            File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".xlsx");
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
            workbook.dispose();
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error generando Excel", e);
        }
    }

    public static Map<String, Object> orderToMap(Order order, FilterRequest filters) {
        Map<String, Object> map = new LinkedHashMap<>();

        // Columnas base (siempre presentes)
        map.put("ID", order.getId());
        map.put("Cliente", order.getCustomer() != null ? order.getCustomer().getFirstName() : "N/A");
        map.put("Fecha Orden", formatDate(order.getOrderDate()));
        map.put("Total", formatCurrency(order.getOrderTotal()));

        // Columnas condicionales según filtros
        if (filters.orderStatus() != null) {
            map.put("Estado", order.getOrderStatus().name());
        }

        if (filters.paymentMethod() != null) {
            map.put("Método Pago", order.getPaymentMethod().name());
        }

        // Columnas de detalle adicional (opcional)
        map.put("Dirección Entrega", order.getDeliveryAddress());
        map.put("Teléfono", order.getDeliveryPhone());

        if (order.getObservations() != null && !order.getObservations().isBlank()) {
            map.put("Observaciones", order.getObservations());
        }

        return map;
    }

    private static String formatDate(LocalDateTime date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A";
    }

    private static String formatCurrency(BigDecimal amount) {
        return amount != null ? String.format("S/ %.2f", amount) : "S/ 0.00";
    }
}
