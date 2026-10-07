package com.boticasanfelipe.modelo;

public class Medicamento {
    public static final int ANIO_ACTUAL = 2026;

    private String codigo;
    private String nombreComercial;
    private String principioActivo;
    private double precioUnitario;
    private int stock;
    private int anioVencimiento;
    private boolean requiereReceta;

    public Medicamento(String codigo, String nombreComercial, String principioActivo,
                       double precioUnitario, int stock, int anioVencimiento,
                       boolean requiereReceta) {
        if (codigo == null || codigo.trim().isEmpty()
                || nombreComercial == null || nombreComercial.trim().isEmpty()) {
            throw new IllegalArgumentException("El código y el nombre son obligatorios.");
        }
        if (precioUnitario < 0 || stock < 0) {
            throw new IllegalArgumentException("El precio y el stock no pueden ser negativos.");
        }
        this.codigo = codigo;
        this.nombreComercial = nombreComercial;
        this.principioActivo = principioActivo;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
        this.anioVencimiento = anioVencimiento;
        this.requiereReceta = requiereReceta;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precioUnitario = precioUnitario;
    }

    public int getStock() {
        return stock;
    }

    public int getAnioVencimiento() {
        return anioVencimiento;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser positiva.");
        }
        stock = stock + cantidad;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reducir debe ser positiva.");
        }
        if (cantidad > stock) {
            throw new IllegalStateException("Stock insuficiente de " + nombreComercial
                    + " (disponible: " + stock + ")");
        }
        stock = stock - cantidad;
    }

    public boolean estaVencido() {
        return anioVencimiento < ANIO_ACTUAL;
    }

    public boolean stockBajo(int umbral) {
        return stock < umbral;
    }

    @Override
    public String toString() {
        return nombreComercial + " (" + codigo + ") - stock: " + stock;
    }
}
