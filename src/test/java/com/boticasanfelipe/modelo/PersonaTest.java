package com.boticasanfelipe.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonaTest {
    @Test
    void dniValidoSeAceptaYSeMuestraEnmascarado() {
        Cliente cliente = new Cliente("Ana Torres", "12345678", "999999999", "Calle 1");
        assertEquals("****5678", cliente.getDniEnmascarado());
        assertTrue(cliente.coincideConDni("12345678"));
        assertFalse(cliente.coincideConDni("87654321"));
    }

    @Test
    void dniConMenosDeOchoDigitosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Juan Pérez", "123", "999999999", "Calle 2"));
    }

    @Test
    void dniConLetrasLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Juan Pérez", "1234567A", "999999999", "Calle 2"));
    }

    @Test
    void nombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("  ", "12345678", "999999999", "Calle 2"));
    }

    @Test
    void toStringNoMuestraElDni() {
        Cliente cliente = new Cliente("Pedro Ramos", "87654321", "900", "Calle 3");
        assertFalse(cliente.toString().contains("87654321"));
        assertFalse(cliente.getDniEnmascarado().contains("87654321"));
    }

    @Test
    void getRolRespondeDistintoSegunLaSubclase() {
        Persona cliente = new Cliente("Marco Salinas", "40011223", "941234567", "Av. Los Ángeles 452");
        Persona quimica = new QuimicoFarmaceutico("Ana Rojas", "45678912", "944112233", "QF-01", "CQFP-8821");
        Persona vendedor = new Vendedor("Luis Paredes", "41234567", "944556677", "VD-01");
        assertEquals("Cliente", cliente.getRol());
        assertEquals("Químico Farmacéutico", quimica.getRol());
        assertEquals("Vendedor", vendedor.getRol());
    }

    @Test
    void soloElQuimicoPuedeDispensarConReceta() {
        PersonalFarmacia quimica = new QuimicoFarmaceutico("Ana Rojas", "45678912", "944112233", "QF-01", "CQFP-8821");
        PersonalFarmacia vendedor = new Vendedor("Luis Paredes", "41234567", "944556677", "VD-01");
        assertTrue(quimica.puedeDispensarConReceta());
        assertFalse(vendedor.puedeDispensarConReceta());
    }
}
