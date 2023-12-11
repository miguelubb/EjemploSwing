package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaProducto extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cSKU;
    private JTextField cNombre;
    private JTextField cCategoria;
    private JTextField cPrecioVenta;
    private JTextField cStockMin;

    public VentanaProducto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        cPrecioVenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(Character.isLetter(e.getKeyChar())){
                    e.consume();
                    return;
                }
                super.keyTyped(e);
            }
        });
        cStockMin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(Character.isLetter(e.getKeyChar())){
                    e.consume();
                    return;
                }
                super.keyTyped(e);
            }
        });
    }

    private void onOK() {
        // add your code here
        try {
            String sku=cSKU.getText();
            String nombre=cNombre.getText();
            String cat=cCategoria.getText();
            int precioVenta = Integer.parseInt(cPrecioVenta.getText());
            int stockMin = Integer.parseInt(cStockMin.getText());
            if(Controlador.getInstance().agregarProducto(sku, nombre, cat, precioVenta, stockMin)){
                Msg.info("producto creado exitosamente");
            }else{
                Msg.alerta("No se ha podido crear el producto porque está duplicado");
            }
        }catch (NumberFormatException ex){
            Msg.error("El precio de venta y/o el stock mínmo no son un numero entero válido");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display(Component component) {
        VentanaProducto dialog = new VentanaProducto();
        dialog.pack();
        dialog.setLocationRelativeTo(component);
        dialog.setVisible(true);
    }
}
