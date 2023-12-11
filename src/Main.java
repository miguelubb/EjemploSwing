import controlador.Controlador;
import vista.VentanaPrincipal;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Controlador ct=Controlador.getInstance();
        ct.agregarProducto("A","Plumon Negro pilot", "Librería", 2000,100 );
        ct.agregaMovimientoEntrada("A", LocalDate.now().minusDays(5), 50, 40,"compra");
        ct.agregaMovimientoEntrada("A", LocalDate.now().minusDays(4), 20, 70,"compra");
        ct.agregaMovimientoEntrada("A", LocalDate.now().minusDays(3), 30, 60,"compra");
        ct.agregaMovimientoEntrada("A", LocalDate.now().minusDays(2), 40, 100,"compra");
        ct.agregaMovimientoSalida("A", LocalDate.now().plusDays(1),30,"venta");
        ct.agregaMovimientoSalida("A", LocalDate.now().plusDays(2),20,"venta");
        ct.agregaMovimientoSalida("A", LocalDate.now().plusDays(3),10,"venta");
        ct.agregaMovimientoSalida("A", LocalDate.now().plusDays(4),10,"venta");

        VentanaPrincipal.display();
        System.exit(0);
    }
}