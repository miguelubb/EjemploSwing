package vista;

import controlador.Controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReporteInventario extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cSKU;
    private JTextField cIni;
    private JTextField cFin;
    private JTable tablaEntradas;
    private JTable tablaSalidas;
    private JScrollPane ScrooltablaEntradas;
    private JScrollPane ScrolltablaSalidas;
    private JLabel cNombre;
    private JLabel cCat;
    private JLabel cVenta;
    private JLabel cPPP;
    private JLabel cMin;
    private JLabel cStock;
    private JLabel cStockValorado;
    private JLabel cUtilidad;
    private DateTimeFormatter formatoFechaChilena=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public ReporteInventario() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        int yearNow=LocalDate.now().getYear();
        cIni.setText(LocalDate.of(yearNow,1,1).format(formatoFechaChilena));
        cFin.setText(LocalDate.of(yearNow,12,31).format(formatoFechaChilena));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        cSKU.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                onSKU();
            }
        });

        cIni.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    LocalDate fecha = LocalDate.parse(cIni.getText(), formatoFechaChilena);
                    cIni.setBorder(cSKU.getBorder());
                    cIni.setText(fecha.format(formatoFechaChilena));
                }catch (DateTimeParseException ex){
                    Msg.alerta(cIni, "Formato de fecha incorrecto");
                    ((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.RED));
                }
            }
        });
        cFin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    LocalDate fecha = LocalDate.parse(cFin.getText(), formatoFechaChilena);
                    cFin.setBorder(cSKU.getBorder());
                    cFin.setText(fecha.format(formatoFechaChilena));

                }catch (DateTimeParseException ex){
                    Msg.alerta("Formato de fecha incorrecto");
                    ((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.RED));
                }
            }
        });

    }

    private void onSKU() {
        String sku=cSKU.getText();
        //actualizo los datos del producto si este existe.
        String[] datosProd=Controlador.getInstance().datosProducto(sku);
        cNombre.setText(datosProd[0]);
        cCat.setText(datosProd[1]);
        cVenta.setText(datosProd[2]);
        cMin.setText(datosProd[3]);
        cStock.setText(datosProd[4]);
        cPPP.setText(datosProd[5]);
        cStockValorado.setText(datosProd[6]);

        //actualización de las tablas.
        //  columnas de ambas tablas
        String[] columnas={"Fecha","Descrición", "Cantidad","Precio","Subtotal"};

        LocalDate ini=LocalDate.parse(cIni.getText(), formatoFechaChilena);
        LocalDate fin=LocalDate.parse(cFin.getText(), formatoFechaChilena);
        //se obtienen los datos para las tablas de entradas y salidas.
        String[][] entradas= Controlador.getInstance().getEntradas(sku, ini,fin);
        String[][] salidas=Controlador.getInstance().getSalidas(sku,ini,fin);


        //se asigna un nuevo modelo a cada tabla para que se actualicen sus datos y con ello
        //se renderice nuevamente la tabla, es decir, se dibuje con los datos nuevos.
        tablaEntradas.setModel(new DefaultTableModel(entradas,columnas));
        tablaSalidas.setModel(new DefaultTableModel(salidas,columnas));

        //alineación de las columnas que van a la derecha (números)
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // Alineación a la derecha
        // Aplicar el renderizador a la columna de Edad (por ejemplo, la columna 1)
        tablaEntradas.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tablaEntradas.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tablaEntradas.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        tablaSalidas.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tablaSalidas.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tablaSalidas.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

    }

    private void onOK() {
        // add your code here
      onSKU();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display(Component component) {
        ReporteInventario dialog = new ReporteInventario();
        dialog.pack();
        dialog.setLocationRelativeTo(component);
        dialog.setVisible(true);
    }


}
