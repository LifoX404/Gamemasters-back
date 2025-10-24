package com.cibertec.GameMaster.utils;

import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.database.entity.OrderItem;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class PdfHelper {
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_SECUNDARIO = new Color(52, 73, 94);
    private static final Color COLOR_GRIS_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_OSCURO = new Color(44, 62, 80);

    public static void agregarEncabezado(Document document, Order orden) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{2, 1});

        // Título y nombre de la empresa
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

        com.lowagie.text.Font fontEmpresa = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, COLOR_PRIMARIO);
        Paragraph empresa = new Paragraph("GameMasters", fontEmpresa);
        leftCell.addElement(empresa);

        com.lowagie.text.Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, COLOR_TEXTO_OSCURO);
        Paragraph subtitulo = new Paragraph("Comprobante de Orden", fontSubtitulo);
        leftCell.addElement(subtitulo);
        leftCell.setPaddingBottom(10);

        headerTable.addCell(leftCell);

        // Número de orden
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        com.lowagie.text.Font fontOrdenNum = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, COLOR_SECUNDARIO);
        Paragraph ordenNum = new Paragraph("ORDEN #" + orden.getId(), fontOrdenNum);
        ordenNum.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(ordenNum);

        com.lowagie.text.Font fontFecha = FontFactory.getFont(FontFactory.HELVETICA, 9, COLOR_TEXTO_OSCURO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaFormateada = orden.getOrderDate().format(formatter);

        Paragraph fecha = new Paragraph("Fecha: " + fechaFormateada, fontFecha);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(fecha);
        rightCell.setPaddingBottom(10);

        headerTable.addCell(rightCell);

        document.add(headerTable);

        // Línea divisoria
        LineSeparator line = new LineSeparator();
        line.setLineColor(COLOR_PRIMARIO);
        line.setLineWidth(2);
        document.add(new Chunk(line));
    }

    public static void agregarInformacionOrden(Document document, Order orden) throws DocumentException {
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new int[]{1, 1});
        infoTable.setSpacingBefore(10);

        com.lowagie.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, COLOR_SECUNDARIO);
        com.lowagie.text.Font fontContenido = FontFactory.getFont(FontFactory.HELVETICA, 10, COLOR_TEXTO_OSCURO);

        // Información del cliente
        PdfPCell clienteCell = new PdfPCell();
        clienteCell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        clienteCell.setBackgroundColor(COLOR_GRIS_CLARO);
        clienteCell.setPadding(10);

        Paragraph tituloCliente = new Paragraph("DATOS DEL CLIENTE", fontTitulo);
        clienteCell.addElement(tituloCliente);
        String firstName = orden.getCustomer().getFirstName() != null ? orden.getCustomer().getFirstName() : "";
        String lastName = orden.getCustomer().getLastName() != null ? orden.getCustomer().getLastName() : "";

        clienteCell.addElement(
                new Paragraph(
                        String.format("Nombre: %s %s", firstName, lastName).trim(),
                        fontContenido
                )
        );

        clienteCell.addElement(new Paragraph(String.format("Correo: %s", orden.getCustomer().getEmail()), fontContenido));

        if (orden.getDeliveryAddress() != null && !orden.getDeliveryAddress().isEmpty()) {
            clienteCell.addElement(new Paragraph("Dirección: " + orden.getDeliveryAddress(), fontContenido));
        }

        infoTable.addCell(clienteCell);

        // Información de pago
        PdfPCell pagoCell = new PdfPCell();
        pagoCell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        pagoCell.setBackgroundColor(COLOR_GRIS_CLARO);
        pagoCell.setPadding(10);

        Paragraph tituloPago = new Paragraph("INFORMACIÓN DE PAGO", fontTitulo);
        pagoCell.addElement(tituloPago);
        pagoCell.addElement(new Paragraph(String.format("Método de pago: %s",orden.getPaymentMethod()), fontContenido));
        pagoCell.addElement(new Paragraph(String.format("Teléfono asignado: %s",orden.getDeliveryPhone()), fontContenido));
        pagoCell.addElement(new Paragraph(String.format("Observaciones: %s",orden.getObservations()), fontContenido));

        infoTable.addCell(pagoCell);

        document.add(infoTable);
    }

    public static void agregarTablaItems(Document document, Order orden) throws DocumentException {
        com.lowagie.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        com.lowagie.text.Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 9, COLOR_TEXTO_OSCURO);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1, 1.2f, 1.5f});
        table.setSpacingBefore(10);

        // Encabezados
        String[] headers = {"Producto", "Cant.", "Precio Unit.", "Subtotal"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
            cell.setBackgroundColor(COLOR_PRIMARIO);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(8);
            cell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        // Items de la orden
        boolean colorAlternado = false;
        for (OrderItem item : orden.getOrderItems()) {
            Color bgColor = colorAlternado ? COLOR_GRIS_CLARO : Color.WHITE;

            // Producto
            PdfPCell cellNombre = new PdfPCell(new Phrase(item.getProductName(), fontContent));
            cellNombre.setBackgroundColor(bgColor);
            cellNombre.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
            cellNombre.setPadding(8);
            cellNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellNombre);

            // Cantidad
            PdfPCell cellCantidad = new PdfPCell(new Phrase(String.valueOf(item.getItemQuantity()), fontContent));
            cellCantidad.setBackgroundColor(bgColor);
            cellCantidad.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
            cellCantidad.setPadding(8);
            cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellCantidad);

            // Precio unitario
            PdfPCell cellPrecio = new PdfPCell(new Phrase(String.format("$%.2f", item.getUnitPrice()), fontContent));
            cellPrecio.setBackgroundColor(bgColor);
            cellPrecio.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
            cellPrecio.setPadding(8);
            cellPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellPrecio);

            // Subtotal
            PdfPCell cellSubtotal = new PdfPCell(new Phrase(String.format("$%.2f", item.getSubtotal()), fontContent));
            cellSubtotal.setBackgroundColor(bgColor);
            cellSubtotal.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
            cellSubtotal.setPadding(8);
            cellSubtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellSubtotal);

            colorAlternado = !colorAlternado;
        }

        document.add(table);
    }

    public static void agregarTotales(Document document, Order orden) throws DocumentException {
        PdfPTable totalesTable = new PdfPTable(2);
        totalesTable.setWidthPercentage(40);
        totalesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalesTable.setSpacingBefore(10);

        com.lowagie.text.Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA, 10, COLOR_TEXTO_OSCURO);
        com.lowagie.text.Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, COLOR_SECUNDARIO);

        // Subtotal
//        if (orden.getSubtotal() > 0) {
//            totalesTable.addCell(crearCeldaTotales("Subtotal:", fontLabel, false));
//            totalesTable.addCell(crearCeldaTotales(String.format("$%.2f", orden.getSubtotal()), fontLabel, true));
//        }
//
//        // Envío
//        if (orden.getCostoEnvio() > 0) {
//            totalesTable.addCell(crearCeldaTotales("Envío:", fontLabel, false));
//            totalesTable.addCell(crearCeldaTotales(String.format("$%.2f", orden.getCostoEnvio()), fontLabel, true));
//        }
//
//        // Impuestos
//        if (orden.getImpuestos() > 0) {
//            totalesTable.addCell(crearCeldaTotales("Impuestos:", fontLabel, false));
//            totalesTable.addCell(crearCeldaTotales(String.format("$%.2f", orden.getImpuestos()), fontLabel, true));
//        }

        // Línea divisoria
        PdfPCell lineCell = new PdfPCell();
        lineCell.setColspan(2);
        lineCell.setBorder(com.lowagie.text.Rectangle.TOP);
        lineCell.setBorderColorTop(COLOR_PRIMARIO);
        lineCell.setBorderWidthTop(1.5f);
        lineCell.setPadding(5);
        totalesTable.addCell(lineCell);

        // Total
        PdfPCell labelTotal = crearCeldaTotales("TOTAL:", fontTotal, false);
        labelTotal.setPaddingTop(5);
        totalesTable.addCell(labelTotal);

        PdfPCell valorTotal = crearCeldaTotales(String.format("$%.2f", orden.getOrderTotal()), fontTotal, true);
        valorTotal.setPaddingTop(5);
        totalesTable.addCell(valorTotal);

        document.add(totalesTable);
    }

    public static PdfPCell crearCeldaTotales(String texto, com.lowagie.text.Font font, boolean alinearDerecha) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(4);
        if (alinearDerecha) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        return cell;
    }

    public static void agregarPiePagina(Document document, Order orden) throws DocumentException {
        Font fontPie = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.GRAY);

        Paragraph notasPie = new Paragraph();
        notasPie.setAlignment(Element.ALIGN_CENTER);

        notasPie.add(new Phrase("Gracias por su compra\n", fontPie));
        notasPie.add(new Phrase("Para cualquier consulta, contáctenos a soporte@gamemasters.com", fontPie));

        document.add(notasPie);
    }
}
