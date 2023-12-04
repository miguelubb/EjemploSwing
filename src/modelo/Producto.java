package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Producto {
    private String sku;
    private String nombre;
    private String categoria;
    //precio de venta
    private int precio;
    private int stockActual;
    private int stockMinimo;
    private double stockEnPesos;

    private ArrayList<Movimiento> misMovimientos=new ArrayList<>();

    public double getStockEnPesos() {
        return stockEnPesos;
    }


    public Producto(String sku, String nombre, String categoria, int precio, int stockMinimo) {
        this.sku = sku;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stockActual = 0;
        this.stockMinimo = stockMinimo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(sku, producto.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }

    @Override
    public String toString() {
        return "producto{" +
                "sku='" + sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stockActual=" + stockActual +
                ", stockMinimo=" + stockMinimo +
                '}';
    }
    public String[] toStringArray(){
        return new String[]{sku, nombre, categoria, String.valueOf(precio), String.valueOf(stockActual), String.valueOf(stockMinimo)};
    }

    public double ppp(){
        return stockEnPesos/stockActual;
    }

    public boolean addMovimiento(Movimiento mov){
        if(mov.getTipo().toUpperCase().equals("ENTRADA")){
            stockActual+=mov.getCantidad();
            stockEnPesos+=mov.getCantidad()*mov.getPrecio();
            misMovimientos.add(mov);
            mov.setProducto(this);
            return true;
        }else if (mov.getTipo().toUpperCase().equals("SALIDA")){
            stockActual-=mov.getCantidad();
            stockEnPesos-=mov.getCantidad()*mov.getPrecio();
            misMovimientos.add(mov);
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Movimiento> getMovimientos(){
        return misMovimientos;
    }

    public ArrayList<Movimiento> getEntradas(LocalDate ini, LocalDate fin) {
        ArrayList<Movimiento> out=new ArrayList<>();
        for (Movimiento mov : misMovimientos) {
            if(mov.getTipo().toUpperCase().equals("ENTRADA") && dateInRange(mov.getFecha(),ini,fin)){
                out.add(mov);
            }
        }
        return out;
    }
    private boolean dateInRange(LocalDate fecha, LocalDate ini, LocalDate fin){
        return fecha.isAfter(ini.minusDays(1)) && fecha.isBefore(fin.plusDays(1));
    }
    public ArrayList<Movimiento> getSalida(LocalDate ini, LocalDate fin) {
        ArrayList<Movimiento> out=new ArrayList<>();
        for (Movimiento mov : misMovimientos) {
            if(mov.getTipo().toUpperCase().equals("SALIDA")&& dateInRange(mov.getFecha(),ini,fin)){
                out.add(mov);
            }
        }
        return out;
    }
}
