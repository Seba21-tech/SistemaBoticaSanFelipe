package com.boticasanfelipe.modelo;

import java.util.ArrayList;

public class Dispensacion {
    public static final String PENDIENTE = "PENDIENTE";
    public static final String COMPLETADA = "COMPLETADA";
    public static final String CANCELADA = "CANCELADA";

    private Cliente cliente;
    private PersonalFarmacia atendidoPor;
    private ArrayList<DetalleDispensacion> detalles = new ArrayList<>();
    private String estado;

    public Dispensacion(Cliente cliente, PersonalFarmacia atendidoPor) {
        if (cliente == null || atendidoPor == null) {
            throw new IllegalArgumentException("Cliente y personal son obligatorios.");
        }
        this.cliente = cliente;
        this.atendidoPor = atendidoPor;
        this.estado = PENDIENTE;
    }

    private int cantidadAgregada(Medicamento medicamento) {
        int suma = 0;
        for (DetalleDispensacion detalle : detalles) {
            if (detalle.getMedicamento() == medicamento) {
                suma = suma + detalle.getCantidad();
            }
        }
        return suma;
    }

    public void agregarItem(Medicamento medicamento) {
        agregarItem(medicamento, 1);
    }

    public void agregarItem(Medicamento medicamento, int cantidad) {
        if (!estado.equals(PENDIENTE)) {
            throw new IllegalStateException("Solo se pueden editar ventas pendientes.");
        }
        if (medicamento == null || cantidad <= 0) {
            throw new IllegalArgumentException("Medicamento y cantidad positiva son obligatorios.");
        }
        if (medicamento.estaVencido()) {
            throw new IllegalArgumentException("No se puede dispensar un medicamento vencido.");
        }
        if (medicamento.isRequiereReceta() && !atendidoPor.puedeDispensarConReceta()) {
            throw new IllegalStateException("Solo un Químico Farmacéutico puede dispensar "
                    + medicamento.getNombreComercial());
        }
        if (cantidadAgregada(medicamento) + cantidad > medicamento.getStock()) {
            throw new IllegalStateException("Stock insuficiente de "
                    + medicamento.getNombreComercial() + " (disponible: "
                    + medicamento.getStock() + ", solicitado: " + cantidad + ")");
        }
        detalles.add(new DetalleDispensacion(medicamento, cantidad));
    }

    public void confirmar() {
        if (!estado.equals(PENDIENTE) || detalles.isEmpty()) {
            throw new IllegalStateException("La venta debe estar pendiente y tener ítems.");
        }
        for (DetalleDispensacion detalle : detalles) {
            Medicamento m = detalle.getMedicamento();
            if (m.estaVencido()) {
                throw new IllegalStateException("Medicamento vencido: " + m.getNombreComercial());
            }
            if (cantidadAgregada(m) > m.getStock()) {
                throw new IllegalStateException("El stock cambió; no se confirmó la venta.");
            }
        }
        for (DetalleDispensacion detalle : detalles) {
            detalle.getMedicamento().reducirStock(detalle.getCantidad());
        }
        estado = COMPLETADA;
        cliente.agregarAlHistorial(this);
    }

    public void cancelar() {
        if (!estado.equals(PENDIENTE)) {
            throw new IllegalStateException("Solo se puede cancelar una venta pendiente.");
        }
        estado = CANCELADA;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (DetalleDispensacion detalle : detalles) {
            total = total + detalle.getSubtotal();
        }
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PersonalFarmacia getAtendidoPor() {
        return atendidoPor;
    }

    public String getEstado() {
        return estado;
    }

    public ArrayList<DetalleDispensacion> getDetalles() {
        return new ArrayList<>(detalles);
    }
}
