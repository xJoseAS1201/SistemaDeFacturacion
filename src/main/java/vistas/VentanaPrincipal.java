package vistas;

import modelo.Factura;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private JTextField campoCliente;
    private JTextField campoNombreProducto;
    private JTextField campoPrecioProducto;
    private JTextField campoCantidadProducto;
    private JTextArea areaFactura;
    private Factura factura;

    public VentanaPrincipal() {
        setTitle("Emisión de Facturas");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Inicializar campos y botones
        campoCliente = new JTextField(20);
        campoNombreProducto = new JTextField(20);
        campoPrecioProducto = new JTextField(20);
        campoCantidadProducto = new JTextField(20);
        areaFactura = new JTextArea(20, 40);
        areaFactura.setEditable(false);

        JButton botonAgregarProducto = new JButton("Agregar Producto");
        JButton botonGenerarFactura = new JButton("Generar Factura");

        // Añadir componentes a la ventana
        add(new JLabel("Cliente:"));
        add(campoCliente);
        add(new JLabel("Nombre Producto:"));
        add(campoNombreProducto);
        add(new JLabel("Precio Producto:"));
        add(campoPrecioProducto);
        add(new JLabel("Cantidad Producto:"));
        add(campoCantidadProducto);
        add(botonAgregarProducto);
        add(botonGenerarFactura);
        add(new JScrollPane(areaFactura));

        // Inicializar la factura
        factura = new Factura("");

        // Acción del botón Agregar Producto
        botonAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = campoNombreProducto.getText();
                    double precio = Double.parseDouble(campoPrecioProducto.getText());
                    int cantidad = Integer.parseInt(campoCantidadProducto.getText());

                    Producto producto = new Producto(nombre, precio, cantidad);
                    factura.agregarProducto(producto);

                    campoNombreProducto.setText("");
                    campoPrecioProducto.setText("");
                    campoCantidadProducto.setText("");
                    actualizarAreaFactura();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos para precio y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón Generar Factura
        botonGenerarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factura.setCliente(campoCliente.getText());
                ExportarPDF.exportar(factura);
                JOptionPane.showMessageDialog(null, "Factura generada exitosamente");
            }
        });
    }

    // Método para actualizar el área de texto de la factura
    private void actualizarAreaFactura() {
        areaFactura.setText("");
        for (Producto producto : factura.getProductos()) {
            areaFactura.append(producto.getNombre() + "\t" + producto.getPrecio() + "\t" + producto.getCantidad() + "\t" + producto.calcularTotal() + "\n");
        }
        areaFactura.append("\nTotal: " + factura.calcularTotal());
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}