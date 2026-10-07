package com.boticasanfelipe.patrones;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Persona;
import com.boticasanfelipe.modelo.QuimicoFarmaceutico;
import com.boticasanfelipe.modelo.Vendedor;

public class FabricaPersonas {
    private static int contadorEmpleados = 0;

    public static Persona crearPersona(String tipo, String nombre, String dni,
                                       String telefono, String datoExtra) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de persona no reconocido: null");
        }
        String t = tipo.toUpperCase();
        if (t.equals("CLIENTE")) {
            return new Cliente(nombre, dni, telefono, datoExtra);
        } else if (t.equals("QUIMICO")) {
            contadorEmpleados++;
            return new QuimicoFarmaceutico(nombre, dni, telefono, "QF-" + contadorEmpleados, datoExtra);
        } else if (t.equals("VENDEDOR")) {
            contadorEmpleados++;
            return new Vendedor(nombre, dni, telefono, "VD-" + contadorEmpleados);
        } else {
            throw new IllegalArgumentException("Tipo de persona no reconocido: " + tipo);
        }
    }
}
