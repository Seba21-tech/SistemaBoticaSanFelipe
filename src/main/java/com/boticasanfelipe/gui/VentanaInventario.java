package com.boticasanfelipe.gui;

import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.patrones.GestorBotica;
import com.boticasanfelipe.reportes.ReporteService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class VentanaInventario extends JFrame {

    private GestorBotica gestor = GestorBotica.getInstancia();
    private ReporteService reportes = new ReporteService();

    private JTextField campoCodigo = new JTextField(8);
    private JTextField campoCantidad = new JTextField(5);
    private JButton botonReponer = new JButton("Reponer stock");
    private JButton botonDisminuir = new JButton("Disminuir stock");
    private JButton botonActualizar = new JButton("Actualizar");
    private JTextArea areaInventario = new JTextArea(14, 60);

    public VentanaInventario() {
        super("Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Código:"));
        panelSuperior.add(campoCodigo);
        panelSuperior.add(new JLabel("Cantidad:"));
        panelSuperior.add(campoCantidad);
        panelSuperior.add(botonReponer);
        panelSuperior.add(botonDisminuir);
        panelSuperior.add(botonActualizar);

        areaInventario.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaInventario), BorderLayout.CENTER);

        botonReponer.addActionListener(e -> reponer());
        botonDisminuir.addActionListener(e -> disminuir());
        botonActualizar.addActionListener(e -> mostrarInventario());

        mostrarInventario();
        pack();
        setLocationRelativeTo(null);
    }

    private void mostrarInventario() {
        areaInventario.setText(reportes.generarInventario(gestor.getInventario()));
    }

    private void reponer() {
        Medicamento medicamento = gestor.buscarMedicamentoPorCodigo(campoCodigo.getText().trim());
        if (medicamento == null) {
            JOptionPane.showMessageDialog(this, "No existe un medicamento con ese código.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            medicamento.aumentarStock(cantidad);
            JOptionPane.showMessageDialog(this, "Stock actualizado: " + medicamento);
            campoCantidad.setText("");
            mostrarInventario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void disminuir() {
        Medicamento medicamento = gestor.buscarMedicamentoPorCodigo(campoCodigo.getText().trim());
        if (medicamento == null) {
            JOptionPane.showMessageDialog(this, "No existe un medicamento con ese código.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            medicamento.reducirStock(cantidad);
            JOptionPane.showMessageDialog(this, "Stock actualizado: " + medicamento);
            campoCantidad.setText("");
            mostrarInventario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
