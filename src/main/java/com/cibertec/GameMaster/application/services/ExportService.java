package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.OrderPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.mapper.ExcelMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.export.FilterRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import com.cibertec.GameMaster.utils.PdfHelper;
import com.cibertec.GameMaster.utils.RenderExcelUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ExportService {

    @Autowired
    private OrderPort port;

    @Autowired
    private ExcelMapper mapper;

    public ResponseEntity<?> exportInExcel(FilterRequest request) {
        List<Order> orders = port.searchOrders(
                request.fromDate(),
                request.toDate(),
                request.orderStatus(),
                request.paymentMethod());

        // Convierte las órdenes a Maps con columnas dinámicas
        Stream<Map<String, Object>> dataStream = orders.stream()
                .map(order -> RenderExcelUtil.orderToMap(order, request));

        File excelFile = RenderExcelUtil.generate(dataStream, mapper.toModel(request));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(excelFile));
    }

    public ResponseEntity<?> exportInPdf(Long orderId) {
        try {
            Order order = port.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, out);
            document.open();

            // Encabezado
            PdfHelper.agregarEncabezado(document, order);
            document.add(new Paragraph("\n"));

            // Información de la orden
            PdfHelper.agregarInformacionOrden(document, order);
            document.add(new Paragraph("\n"));

            // Tabla de items
            PdfHelper.agregarTablaItems(document, order);
            document.add(new Paragraph("\n"));

            // Totales
            PdfHelper.agregarTotales(document, order);
            document.add(new Paragraph("\n"));

            // Pie de página
            PdfHelper.agregarPiePagina(document, order);

            document.close();

            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=order_" + orderId + ".pdf");

            // Devolver respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));

        } catch (DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el documento PDF.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }


}
