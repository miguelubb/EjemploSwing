package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movimiento {
    private LocalDate fecha;
    private String tipo;
    private int cantidad;
    private double precio;
    private String descripcion;

    private Producto producto;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Movimiento(LocalDate fecha, String tipo, int cantidad, double ppp, String descripcion) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precio = ppp;
        this.descripcion = descripcion;
    }

    public String[] toStringArr(){
        return new String[]{fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                tipo, String.valueOf(cantidad),String.valueOf(precio), descripcion};
    }
}
