package com.boticasanfelipe.gui;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Dispensacion;
import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.modelo.PersonalFarmacia;
import com.boticasanfelipe.patrones.GestorBotica;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class VentanaVenta extends JFrame {
    private GestorBotica gestor = GestorBotica.getInstancia();
    private Dispensacion ventaActual = null;

    private JTextField campoDni = new JTextField();
    private JTextField campoEmpleado = new JTextField();
    private JTextField campoMedicamento = new JTextField();
    private JTextField campoCantidad = new JTextField();

    private JButton botonIniciar = new JButton("Iniciar venta");
    private JButton botonAgregar = new JButton("Agregar ítem");
    private JButton botonConfirmar = new JButton("Confirmar venta");
    private JButton botonCancelar = new JButton("Cancelar venta");
    private JButton botonLimpiar = new JButton("Limpiar");
    private JButton botonActualizar = new JButton("Actualizar datos");
    private JTextArea areaDetalle = new JTextArea(12, 40);

    public VentanaVenta() {
        super("Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelDatos.add(new JLabel("DNI del cliente:"));
        panelDatos.add(campoDni);
        panelDatos.add(new JLabel("Código del empleado (ej. QF-1):"));
        panelDatos.add(campoEmpleado);
        panelDatos.add(new JLabel("Código del medicamento:"));
        panelDatos.add(campoMedicamento);
        panelDatos.add(new JLabel("Cantidad:"));
        panelDatos.add(campoCantidad);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIniciar);
        panelBotones.add(botonAgregar);
        panelBotones.add(botonConfirmar);
        panelBotones.add(botonCancelar);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonActualizar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelDatos, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        areaDetalle.setEditable(false);
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        botonIniciar.addActionListener(e -> iniciarVenta());
        botonAgregar.addActionListener(e -> agregarItem());
        botonConfirmar.addActionListener(e -> confirmarVenta());
        botonCancelar.addActionListener(e -> cancelarVenta());
        botonLimpiar.addActionListener(e -> limpiar());
        botonActualizar.addActionListener(e -> actualizarDatos());

        mostrarDatosDisponibles();
        pack();
        setLocationRelativeTo(null);
    }

    private void actualizarDatos() {
        areaDetalle.setText("");
        mostrarDatosDisponibles();
    }

    private void mostrarDatosDisponibles() {
        areaDetalle.append("Clientes (DNI enmascarado; escribe el DNI completo):\n");
        for (Cliente c : gestor.getClientes()) {
            areaDetalle.append("  " + c.getDniEnmascarado() + " - " + c.getNombre() + "\n");
        }
        areaDetalle.append("Personal (código):\n");
        for (PersonalFarmacia p : gestor.getPersonal()) {
            areaDetalle.append("  " + p.getCodigoEmpleado() + " - " + p.getNombre() + "\n");
        }
        areaDetalle.append("Medicamentos (código):\n");
        for (Medicamento m : gestor.getInventario()) {
            areaDetalle.append("  " + m + "\n");
        }
        areaDetalle.append("------------------------------\n");
    }

    private boolean esCodigoEmpleadoValido(String codigo) {
        return codigo.matches("(?i)(QF|VD)-\\d+");
    }

    private boolean esDniValido(String dni) {
        return dni.matches("\\d{8}");
    }

    private void iniciarVenta() {
        String dni = campoDni.getText().trim();
        String codigo = campoEmpleado.getText().trim();

        if (!esDniValido(dni)) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos.");
            return;
        }
        if (!esCodigoEmpleadoValido(codigo)) {
            JOptionPane.showMessageDialog(this,
                    "Código de empleado inválido. Debe tener el formato QF-1 (Químico) o VD-2 (Vendedor), no un nombre.");
            return;
        }

        Cliente cliente = gestor.buscarClientePorDni(dni);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "No existe un cliente registrado con ese DNI.");
            return;
        }
        PersonalFarmacia empleado = gestor.buscarPersonalPorCodigo(codigo);
        if (empleado == null) {
            JOptionPane.showMessageDialog(this, "No existe un empleado registrado con el código " + codigo.toUpperCase() + ".");
            return;
        }
        ventaActual = new Dispensacion(cliente, empleado);
        areaDetalle.append("Venta iniciada para " + cliente.getNombre()
                + ", atendida por " + empleado.getNombre() + "\n");
    }

    private void agregarItem() {
        if (ventaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debes iniciar una venta.");
            return;
        }
        Medicamento medicamento = gestor.buscarMedicamentoPorCodigo(campoMedicamento.getText().trim());
        if (medicamento == null) {
            JOptionPane.showMessageDialog(this, "No existe un medicamento con ese código.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            ventaActual.agregarItem(medicamento, cantidad);
            areaDetalle.append(cantidad + " x " + medicamento.getNombreComercial() + "\n");
            campoCantidad.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void confirmarVenta() {
        if (ventaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay una venta en curso.");
            return;
        }
        try {
            ventaActual.confirmar();
            gestor.registrarDispensacion(ventaActual);
            areaDetalle.append(String.format("Venta confirmada. Total: S/ %.2f%n", ventaActual.calcularTotal()));
            ventaActual = null;
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void cancelarVenta() {
        if (ventaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay una venta en curso.");
            return;
        }
        ventaActual.cancelar();
        areaDetalle.append("Venta cancelada.\n");
        ventaActual = null;
    }

    private void limpiar() {
        campoDni.setText("");
        campoEmpleado.setText("");
        campoMedicamento.setText("");
        campoCantidad.setText("");
    }
}
