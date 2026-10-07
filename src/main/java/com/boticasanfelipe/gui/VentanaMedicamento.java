package com.boticasanfelipe.gui;

import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.patrones.GestorBotica;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class VentanaMedicamento extends JFrame {
    private JTextField campoCodigo = new JTextField();
    private JTextField campoNombre = new JTextField();
    private JTextField campoPrincipio = new JTextField();
    private JTextField campoPrecio = new JTextField();
    private JTextField campoStock = new JTextField();
    private JTextField campoAnio = new JTextField();
    private JCheckBox checkReceta = new JCheckBox("Sí requiere receta");
    private JButton botonRegistrar = new JButton("Registrar");
    private JButton botonLimpiar = new JButton("Limpiar");

    public VentanaMedicamento() {
        super("Registrar medicamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 5, 5));

        add(new JLabel("Código:"));
        add(campoCodigo);
        add(new JLabel("Nombre:"));
        add(campoNombre);
        add(new JLabel("Principio activo:"));
        add(campoPrincipio);
        add(new JLabel("Precio (S/):"));
        add(campoPrecio);
        add(new JLabel("Stock:"));
        add(campoStock);
        add(new JLabel("Año de vencimiento:"));
        add(campoAnio);
        add(new JLabel("Receta:"));
        add(checkReceta);
        add(botonRegistrar);
        add(botonLimpiar);

        botonRegistrar.addActionListener(e -> registrar());
        botonLimpiar.addActionListener(e -> limpiar());

        setSize(420, 320);
        setLocationRelativeTo(null);
    }

    private void registrar() {
        if (campoCodigo.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código y el nombre son obligatorios.");
            return;
        }
        try {
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int stock = Integer.parseInt(campoStock.getText().trim());
            int anio = Integer.parseInt(campoAnio.getText().trim());

            Medicamento medicamento = new Medicamento(campoCodigo.getText().trim(),
                    campoNombre.getText().trim(), campoPrincipio.getText().trim(),
                    precio, stock, anio, checkReceta.isSelected());
            GestorBotica.getInstancia().registrarMedicamento(medicamento);

            JOptionPane.showMessageDialog(this, "Medicamento registrado correctamente.");
            limpiar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, stock y año deben ser números.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiar() {
        campoCodigo.setText("");
        campoNombre.setText("");
        campoPrincipio.setText("");
        campoPrecio.setText("");
        campoStock.setText("");
        campoAnio.setText("");
        checkReceta.setSelected(false);
    }
}
