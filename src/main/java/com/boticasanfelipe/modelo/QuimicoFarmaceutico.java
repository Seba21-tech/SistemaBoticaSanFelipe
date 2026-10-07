package com.boticasanfelipe.modelo;

public class QuimicoFarmaceutico extends PersonalFarmacia {
    private String numeroColegiatura;

    public QuimicoFarmaceutico(String nombre, String dni, String telefono,
                               String codigoEmpleado, String numeroColegiatura) {
        super(nombre, dni, telefono, codigoEmpleado);
        this.numeroColegiatura = numeroColegiatura;
    }

    public String getNumeroColegiatura() {
        return numeroColegiatura;
    }

    @Override
    public boolean puedeDispensarConReceta() {
        return true;
    }

    @Override
    public String getRol() {
        return "Químico Farmacéutico";
    }
}
