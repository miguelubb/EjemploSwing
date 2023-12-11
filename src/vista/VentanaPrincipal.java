package vista;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

public class VentanaPrincipal extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton nuevoProductoButton;
    private JButton nuevoMovimientoButton;
    private JButton inventarioGeneralButton;
    private JButton inventarioPorProductoButton;

    public VentanaPrincipal() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        nuevoProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirNuevoProducto();
            }
        });
        inventarioGeneralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaInventario();
            }
        });
        nuevoMovimientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirNuevoMovimiento();
            }
        });
    }

    private void abrirNuevoMovimiento() {
        VentanaMovimientos.display(this);
    }

    private void abrirVentanaInventario() {
        ReporteInventario.display(this);
    }

    private void abrirNuevoProducto() {
        VentanaProducto.display(this);
    }

    private void onCancel() {
        if (JOptionPane.showConfirmDialog(null,
               "¿ desea salir ?",
                "confirmación",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
               dispose();
        }
    }

    public static void display() {
        VentanaPrincipal dialog = new VentanaPrincipal();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
}
