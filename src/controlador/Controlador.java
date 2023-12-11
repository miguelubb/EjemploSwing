package controlador;

import modelo.Movimiento;
import modelo.Producto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class Controlador {
    private static Controlador instance=null;
    ArrayList<Producto> produtos;
    ArrayList<Movimiento> movimientos;

    private Controlador(){
        produtos=new ArrayList<>();
        movimientos=new ArrayList<>();
    }
    public static Controlador getInstance(){
        if(instance==null){
            instance=new Controlador();
        }
        return instance;
    }

    public boolean agregarProducto(String sku, String nombre, String categoria, int precioVenta, int stockMinimo){
        Producto p=new Producto(sku, nombre, categoria, precioVenta, stockMinimo);
        if(produtos.contains(p)){
            return false;
        }
        return produtos.add(p);
    }

    public boolean agregaMovimientoSalida(String sku, LocalDate fecha, int cantidad, String descripcion){
        Optional<Producto> producto=findBySKU(sku);
        if(producto.isPresent()){
            Producto p=producto.get();
            double ppp=p.ppp();
            Movimiento mov=new Movimiento(fecha, "SALIDA", cantidad, ppp, descripcion);
            p.addMovimiento(mov);
            return movimientos.add(mov);
        }
        return false;
    }

    private Optional<Producto> findBySKU(String sku){
        return produtos.stream().filter(p-> p.getSku().equals(sku)).findFirst();
    }

    public String[][] getEntradas(String sku,LocalDate ini, LocalDate fin){

        Optional<Producto> producto=findBySKU(sku);
        if(producto.isPresent()) {
            Producto p = producto.get();
            ArrayList<Movimiento> entradas=p.getEntradas(ini, fin);
            String[][] out=new String[entradas.size()+1][5];
            int i=0,sumUnidades=0;double sumValor=0;
            for (Movimiento mov : entradas) {
                out[i][0]=mov.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                out[i][1]=mov.getDescripcion();
                out[i][2]= String.format("%,d",mov.getCantidad());
                out[i][3]= String.format("%,.2f",mov.getPrecio());
                out[i][4]=String.format("%,.2f",mov.getCantidad()*mov.getPrecio());
                sumUnidades+=mov.getCantidad();
                sumValor+=mov.getCantidad()*mov.getPrecio();
                i++;
            }
            out[i][0]="Totales:";
            out[i][2]= String.format("%,d",sumUnidades);
            out[i][4]= String.format("%,.2f",sumValor);
            return out;
        }
        return new String[0][0];
    }


    public String[][] getSalidas(String sku,LocalDate ini, LocalDate fin){

        Optional<Producto> producto=findBySKU(sku);
        if(producto.isPresent()) {
            Producto p = producto.get();
            ArrayList<Movimiento> salidas=p.getSalida(ini, fin);
            String[][] out=new String[salidas.size()+1][5];
            int i=0,sumUnidades=0;double sumValor=0;
            for (Movimiento mov : salidas) {
                out[i][0]=mov.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                out[i][1]=mov.getDescripcion();
                out[i][2]= String.format("%,d",mov.getCantidad());
                out[i][3]= String.format("%,.2f",mov.getPrecio());
                out[i][4]=String.format("%,.2f",mov.getCantidad()*mov.getPrecio());
                sumUnidades+=mov.getCantidad();
                sumValor+=mov.getCantidad()*mov.getPrecio();
                i++;
            }
            out[i][0]="Totales:";
            out[i][2]= String.format("%,d",sumUnidades);
            out[i][4]= String.format("%,.2f",sumValor);
            return out;
        }
        return new String[0][0];
    }

    public boolean agregaMovimientoEntrada(String sku, LocalDate fecha, int cantidad, int precio, String descripcion) {
        Optional<Producto> producto=findBySKU(sku);
        if(producto.isPresent()){
            Producto p=producto.get();
            Movimiento mov=new Movimiento(fecha, "ENTRADA", cantidad, precio, descripcion);
            p.addMovimiento(mov);
            return movimientos.add(mov);
        }
        return false;
    }

    public String[] datosProducto(String sku){
        Optional<Producto> opP=findBySKU(sku);
        if(opP.isPresent()){
            Producto p=opP.get();
            /*
           p.nombre , p.categoria, p.precio, p.stockActual, p.stockMinimo
             */
            return new String[]{
                    p.getNombre(),
                    p.getCategoria(),
                    String.format("%,d",p.getPrecio()),
                    String.format("%,d",p.getStockMinimo()),
                    String.format("%,d",p.getStockActual()),
                    String.format("%,.2f",p.ppp()),
                    String.format("%,.2f",p.getStockEnPesos())
            };
        }
        return new String[]{"-","-","-","-","-","-","-"};
    }

    public String[] ListaDeProductos() {
        return produtos.stream().map(p -> p.getSku()+" - "+ p.getNombre() ).toList().toArray(new String[0]);
    }
}
