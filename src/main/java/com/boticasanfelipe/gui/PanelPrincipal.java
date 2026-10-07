package com.boticasanfelipe.gui;

import com.boticasanfelipe.patrones.GestorBotica;
import com.boticasanfelipe.reportes.ReporteService;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class PanelPrincipal extends JPanel {
    private ControladorVentana controlador;

    private JButton botonMedicamentos = new JButton("Medicamentos");
    private JButton botonInventario = new JButton("Inventario");
    private JButton botonPersonas = new JButton("Personas");
    private JButton botonVentas = new JButton("Ventas");
    private JButton botonReporte = new JButton("Ver reporte");
    private JButton botonCerrarSesion = new JButton("Cerrar sesión");
    private JTextArea areaReporte = new JTextArea(12, 40);

    public PanelPrincipal(ControladorVentana controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonMedicamentos);
        panelBotones.add(botonInventario);
        panelBotones.add(botonPersonas);
        panelBotones.add(botonVentas);
        panelBotones.add(botonReporte);
        panelBotones.add(botonCerrarSesion);

        areaReporte.setEditable(false);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        botonMedicamentos.addActionListener(e -> new VentanaMedicamento().setVisible(true));
        botonInventario.addActionListener(e -> new VentanaInventario().setVisible(true));
        botonPersonas.addActionListener(e -> new VentanaPersona().setVisible(true));
        botonVentas.addActionListener(e -> new VentanaVenta().setVisible(true));
        botonReporte.addActionListener(e -> verReporte());
        botonCerrarSesion.addActionListener(e -> controlador.cambiarPanel(new IngresoSistema(controlador)));
    }

    private void verReporte() {
        GestorBotica gestor = GestorBotica.getInstancia();
        ReporteService reportes = new ReporteService();
        areaReporte.setText(reportes.generarResumen(gestor.getInventario(), gestor.getDispensaciones()));
    }
}
