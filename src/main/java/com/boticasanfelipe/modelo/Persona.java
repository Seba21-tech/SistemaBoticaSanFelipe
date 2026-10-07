package com.boticasanfelipe.modelo;

import com.boticasanfelipe.seguridad.Encriptador;

public abstract class Persona {
    private String nombre;
    private String dniEncriptado;
    private String dniEnmascarado;
    private String telefono;

    public Persona(String nombre, String dni, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!esDniValido(dni)) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
        this.nombre = nombre;
        this.dniEncriptado = Encriptador.encriptarSHA256(dni);
        this.dniEnmascarado = "****" + dni.substring(4);
        this.telefono = telefono;
    }

    private boolean esDniValido(String dni) {
        if (dni == null || dni.length() != 8) {
            return false;
        }
        for (int i = 0; i < dni.length(); i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDniEnmascarado() {
        return dniEnmascarado;
    }

    public boolean coincideConDni(String dniIngresado) {
        if (dniIngresado == null) {
            return false;
        }
        return dniEncriptado.equals(Encriptador.encriptarSHA256(dniIngresado));
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public abstract String getRol();

    @Override
    public String toString() {
        return getRol() + ": " + nombre;
    }
}
