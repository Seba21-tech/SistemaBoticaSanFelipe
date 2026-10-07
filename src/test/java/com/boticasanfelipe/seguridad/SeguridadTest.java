package com.boticasanfelipe.seguridad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeguridadTest {
    @Test
    void sha256DeAdmin123DaElHashConocido() {
        assertEquals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9",
                Encriptador.encriptarSHA256("admin123"));
    }

    @Test
    void elHashNoContieneElTextoOriginalYEsSiempreIgual() {
        String hash = Encriptador.encriptarSHA256("miClave");
        assertFalse(hash.contains("miClave"));
        assertEquals(hash, Encriptador.encriptarSHA256("miClave"));
    }

    @Test
    void elLoginAceptaCredencialesCorrectas() {
        assertTrue(new GestionSeguridad().autenticar("admin", "admin123"));
    }

    @Test
    void elLoginRechazaContrasenaIncorrectaUsuarioIncorrectoONulos() {
        GestionSeguridad seguridad = new GestionSeguridad();
        assertFalse(seguridad.autenticar("admin", "otraClave"));
        assertFalse(seguridad.autenticar("otro", "admin123"));
        assertFalse(seguridad.autenticar(null, "admin123"));
    }
}
