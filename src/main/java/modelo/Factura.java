package modelo;

import java.util.ArrayList;
import java.util.List;

public class Factura {
    private String cliente;
    private List<Producto> productos;

    // Constructor
    public Factura(String cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
    }

    // Getters y Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // Método para añadir un producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    // Método para calcular el total de la factura
    public double calcularTotal() {
        return productos.stream().mapToDouble(Producto::calcularTotal).sum();
    }
}