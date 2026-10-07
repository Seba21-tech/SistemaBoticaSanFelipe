package com.boticasanfelipe.modelo;

public class DetalleDispensacion {
    private Medicamento medicamento;
    private int cantidad;

    public DetalleDispensacion(Medicamento medicamento, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.medicamento = medicamento;
        this.cantidad = cantidad;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return medicamento.getPrecioUnitario() * cantidad;
    }
}
