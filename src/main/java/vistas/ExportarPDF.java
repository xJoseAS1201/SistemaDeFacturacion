package vistas;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import modelo.Factura;
import modelo.Producto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExportarPDF {

    public static void exportar(Factura factura) {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("factura.pdf"));
            documento.open();
            documento.add(new Paragraph("Factura"));
            documento.add(new Paragraph("Cliente: " + factura.getCliente()));
            documento.add(new Paragraph("\n"));

            for (Producto producto : factura.getProductos()) {
                documento.add(new Paragraph(producto.getNombre() + "\t" + producto.getPrecio() + "\t" + producto.getCantidad() + "\t" + producto.calcularTotal()));
            }
            documento.add(new Paragraph("\nTotal: " + factura.calcularTotal()));

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } finally {
            if (documento != null) {
                documento.close();
            }
        }
    }
}