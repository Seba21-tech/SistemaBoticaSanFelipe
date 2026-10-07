package com.boticasanfelipe.modelo;

public abstract class PersonalFarmacia extends Persona {
    private String codigoEmpleado;

    public PersonalFarmacia(String nombre, String dni, String telefono, String codigoEmpleado) {
        super(nombre, dni, telefono);
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public abstract boolean puedeDispensarConReceta();

    @Override
    public String toString() {
        return getRol() + ": " + getNombre() + " (" + codigoEmpleado + ")";
    }
}
