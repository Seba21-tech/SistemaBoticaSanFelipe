package com.boticasanfelipe.gui;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Persona;
import com.boticasanfelipe.modelo.PersonalFarmacia;
import com.boticasanfelipe.patrones.FabricaPersonas;
import com.boticasanfelipe.patrones.GestorBotica;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class VentanaPersona extends JFrame {
    private JComboBox<String> comboTipo = new JComboBox<String>(
            new String[]{"CLIENTE", "QUIMICO", "VENDEDOR"});
    private JTextField campoNombre = new JTextField();
    private JTextField campoDni = new JTextField();
    private JTextField campoTelefono = new JTextField();
    private JTextField campoExtra = new JTextField();
    private JButton botonRegistrar = new JButton("Registrar");
    private JButton botonLimpiar = new JButton("Limpiar");

    public VentanaPersona() {
        super("Registrar persona");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Tipo:"));
        add(comboTipo);
        add(new JLabel("Nombre:"));
        add(campoNombre);
        add(new JLabel("DNI (8 dígitos):"));
        add(campoDni);
        add(new JLabel("Teléfono:"));
        add(campoTelefono);
        add(new JLabel("Dirección (cliente) o N° colegiatura (químico):"));
        add(campoExtra);
        add(botonRegistrar);
        add(botonLimpiar);

        botonRegistrar.addActionListener(e -> registrar());
        botonLimpiar.addActionListener(e -> limpiar());

        setSize(520, 280);
        setLocationRelativeTo(null);
    }

    private void registrar() {
        if (campoNombre.getText().trim().isEmpty() || campoDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y el DNI son obligatorios.");
            return;
        }
        try {
            String tipo = (String) comboTipo.getSelectedItem();
            Persona persona = FabricaPersonas.crearPersona(tipo, campoNombre.getText().trim(),
                    campoDni.getText().trim(), campoTelefono.getText().trim(),
                    campoExtra.getText().trim());

            GestorBotica gestor = GestorBotica.getInstancia();
            if (tipo.equals("CLIENTE")) {
                gestor.registrarCliente((Cliente) persona);
                JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.");
            } else {
                PersonalFarmacia empleado = (PersonalFarmacia) persona;
                gestor.registrarPersonal(empleado);
                JOptionPane.showMessageDialog(this,
                        "Personal registrado. Código de empleado: " + empleado.getCodigoEmpleado());
            }
            limpiar();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiar() {
        comboTipo.setSelectedIndex(0);
        campoNombre.setText("");
        campoDni.setText("");
        campoTelefono.setText("");
        campoExtra.setText("");
    }
}
