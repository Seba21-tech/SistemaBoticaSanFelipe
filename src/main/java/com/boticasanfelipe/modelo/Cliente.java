package com.boticasanfelipe.modelo;

import java.util.ArrayList;

public class Cliente extends Persona {
    private String direccion;
    private ArrayList<Dispensacion> historial = new ArrayList<>();

    public Cliente(String nombre, String dni, String telefono, String direccion) {
        super(nombre, dni, telefono);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void agregarAlHistorial(Dispensacion dispensacion) {
        historial.add(dispensacion);
    }

    public ArrayList<Dispensacion> getHistorial() {
        return new ArrayList<>(historial);
    }

    @Override
    public String getRol() {
        return "Cliente";
    }
}
