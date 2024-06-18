package Main;

import modelo.Factura;
import vistas.VentanaPrincipal;

public class Main {

    private Factura factura;
    private VentanaPrincipal ventana;

    public Main() {
        factura = new Factura("");
        ventana = new VentanaPrincipal();
    }

    public void iniciar() {
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        Main controlador = new Main();
        controlador.iniciar();
    }
}