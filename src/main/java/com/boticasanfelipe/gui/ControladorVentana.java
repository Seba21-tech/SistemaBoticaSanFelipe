package com.boticasanfelipe.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ControladorVentana extends JFrame {
    public ControladorVentana() {
        setTitle("Sistema Botica San Felipe");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new IngresoSistema(this), BorderLayout.CENTER);
    }

    public void cambiarPanel(JPanel nuevoPanel) {
        getContentPane().removeAll();
        add(nuevoPanel, BorderLayout.CENTER);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {
        new ControladorVentana().setVisible(true);
    }
}
