package vistas;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import modelo.Factura;
import modelo.Producto;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExportarPDF {

    public static void exportar(Factura factura) {
        UIManager.put("FileChooserUI", "javax.swing.plaf.metal.MetalFileChooserUI");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Factura como PDF");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            Document documento = new Document();
            try {
                PdfWriter.getInstance(documento, new FileOutputStream(filePath));
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
                JOptionPane.showMessageDialog(null, "Error al generar la factura en PDF", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (documento != null) {
                    documento.close();
                    JOptionPane.showMessageDialog(null, "Factura generada exitosamente");
                }
            }
        } else {
            System.out.println("El usuario canceló la operación.");
        }
    }
}