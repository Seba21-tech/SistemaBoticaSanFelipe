package com.boticasanfelipe.gui;

import com.boticasanfelipe.seguridad.GestionSeguridad;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class IngresoSistema extends JPanel {
    private ControladorVentana controlador;
    private GestionSeguridad seguridad = new GestionSeguridad();

    private JTextField campoUsuario = new JTextField();
    private JPasswordField campoContrasena = new JPasswordField();
    private JButton botonIngresar = new JButton("INGRESAR");

    public IngresoSistema(ControladorVentana controlador) {
        this.controlador = controlador;

        setLayout(new GridLayout(6, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        add(new JLabel("Bienvenido al sistema", JLabel.CENTER));
        add(new JLabel("Usuario:"));
        add(campoUsuario);
        add(new JLabel("Contraseña:"));
        add(campoContrasena);
        add(botonIngresar);

        botonIngresar.addActionListener(e -> ingresar());
        campoContrasena.addActionListener(e -> ingresar());
    }

    private void ingresar() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe tu usuario y tu contraseña.");
            return;
        }

        if (seguridad.autenticar(usuario, contrasena)) {
            controlador.cambiarPanel(new PanelPrincipal(controlador));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            campoContrasena.setText("");
        }
    }
}
