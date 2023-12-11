package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VentanaMovimientos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cFecha;
    private JTextField cCantidad;
    private JTextField cPrecio;
    private JTextField cDescrip;
    private JRadioButton entradaRadioButton;
    private JRadioButton salidaRadioButton;
    private JComboBox cProducto;

    public VentanaMovimientos() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        String[] productos= Controlador.getInstance().ListaDeProductos();
        cFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //llenado de comboBox de producto
        cProducto.setModel(new DefaultComboBoxModel(productos));
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
        salidaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cPrecio.setEnabled(false);
            }
        });
        entradaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cPrecio.setEnabled(true);
            }
        });
    }

    private void onOK() {
        // add your code here
        try {
            LocalDate fecha=LocalDate.parse(cFecha.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String[] prod = ((String)cProducto.getSelectedItem()).split(" - ");
            String sku=prod[0];
            int cantidad= Integer.parseInt(cCantidad.getText());

            String desc=cDescrip.getText();
            if(entradaRadioButton.isSelected()){
                int precio= Integer.parseInt(cPrecio.getText());
                if(Controlador.getInstance().agregaMovimientoEntrada
                        (sku,fecha,cantidad,precio, desc)){
                   Msg.info("Moviento de entrada agregado con éxito");
                }else{
                    Msg.alerta("Producto no encontrado");
                }
            }else{
                if(Controlador.getInstance().agregaMovimientoSalida(sku,
                        fecha,cantidad, desc)){
                    Msg.info("Moviento de salida agregado con éxito");
                }else{
                    Msg.alerta("Producto no encontrado");
                }
            }

        }catch (DateTimeParseException dte){
            Msg.alerta("error en formato de fecha");
        }catch (NumberFormatException numberFormatException){
            Msg.alerta("La cantidad y/o precio deben ser un número entero");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display(Component c) {
        VentanaMovimientos dialog = new VentanaMovimientos();
        dialog.pack();
        dialog.setLocationRelativeTo(c);
        dialog.setVisible(true);

    }
}
