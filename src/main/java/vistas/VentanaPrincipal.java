package vistas;

import modelo.*;


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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Tamaño de la ventana

        // Crear componentes
        campoCliente = new JTextField(20);
        campoNombreProducto = new JTextField(20);
        campoPrecioProducto = new JTextField(20);
        campoCantidadProducto = new JTextField(20);
        areaFactura = new JTextArea(10, 40); // Tamaño inicial del JTextArea
        areaFactura.setEditable(false);

        // Botones
        JButton botonAgregarProducto = new JButton("Agregar Producto");
        JButton botonGenerarFactura = new JButton("Generar Factura");

        // Panel para datos de entrada
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.add(new JLabel("Cliente:"));
        panelDatos.add(campoCliente);
        panelDatos.add(new JLabel("Nombre Producto:"));
        panelDatos.add(campoNombreProducto);
        panelDatos.add(new JLabel("Precio Producto:"));
        panelDatos.add(campoPrecioProducto);
        panelDatos.add(new JLabel("Cantidad Producto:"));
        panelDatos.add(campoCantidadProducto);

        // Panel para botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonAgregarProducto);
        panelBotones.add(botonGenerarFactura);

        // Organización de la ventana
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelDatos, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(areaFactura), BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        // Inicializar factura
        factura = new Factura("");

        // Acción del botón Agregar Producto
        botonAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        // Acción del botón Generar Factura
        botonGenerarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });
    }

    // Método para agregar un producto a la factura
    private void agregarProducto() {
        try {
            String nombre = campoNombreProducto.getText();
            double precio = Double.parseDouble(campoPrecioProducto.getText());
            int cantidad = Integer.parseInt(campoCantidadProducto.getText());

            Producto producto = new Producto(nombre, precio, cantidad);
            factura.agregarProducto(producto);

            // Actualizar área de factura
            actualizarAreaFactura();

            // Limpiar campos de entrada
            campoNombreProducto.setText("");
            campoPrecioProducto.setText("");
            campoCantidadProducto.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para precio y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para generar la factura en PDF
    private void generarFactura() {
        factura.setCliente(campoCliente.getText());
        ExportarPDF.exportar(factura);
    }

    // Método para actualizar el área de texto con los productos de la factura
    private void actualizarAreaFactura() {
        areaFactura.setText("");
        for (Producto producto : factura.getProductos()) {
            areaFactura.append(producto.getNombre() + "\t" + producto.getPrecio() + "\t" + producto.getCantidad() + "\t" + producto.calcularTotal() + "\n");
        }
        areaFactura.append("\nTotal: " + factura.calcularTotal());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}