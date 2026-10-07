package com.boticasanfelipe.modelo;

public class Vendedor extends PersonalFarmacia {
    public Vendedor(String nombre, String dni, String telefono, String codigoEmpleado) {
        super(nombre, dni, telefono, codigoEmpleado);
    }

    @Override
    public boolean puedeDispensarConReceta() {
        return false;
    }

    @Override
    public String getRol() {
        return "Vendedor";
    }
}
