package com.boticasanfelipe.seguridad;

public class GestionSeguridad {
    private Usuario adminValido = new Usuario("admin", Encriptador.encriptarSHA256("admin123"));

    public boolean autenticar(String usuarioIngresado, String passwordIngresada) {
        if (usuarioIngresado == null || passwordIngresada == null) {
            return false;
        }
        String passwordEncriptada = Encriptador.encriptarSHA256(passwordIngresada);
        return adminValido.getUsuario().equals(usuarioIngresado)
                && adminValido.getContrasena().equals(passwordEncriptada);
    }
}
