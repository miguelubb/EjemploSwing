package vista;

import javax.swing.*;
import java.awt.*;

public class Msg {
    public static void error(Component comp, String msg){
        JOptionPane.showMessageDialog(comp, msg, "Error",JOptionPane.ERROR_MESSAGE );
    }
    public static void error(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error",JOptionPane.ERROR_MESSAGE );
    }

    public static void info(Component comp, String msg){
        JOptionPane.showMessageDialog(comp, msg, "Información",JOptionPane.INFORMATION_MESSAGE );
    }
    public static void info(String msg){
        JOptionPane.showMessageDialog(null, msg, "Información",JOptionPane.INFORMATION_MESSAGE );
    }

    public static void alerta(Component comp, String msg){
        JOptionPane.showMessageDialog(comp, msg, "Alerta",JOptionPane.WARNING_MESSAGE );
    }
    public static void alerta(String msg){
        JOptionPane.showMessageDialog(null, msg, "Alerta",JOptionPane.WARNING_MESSAGE );
    }
}
