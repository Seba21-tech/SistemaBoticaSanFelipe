package com.boticasanfelipe.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicamentoTest {
    @Test
    void reducirStockDescuentaCorrectamente() {
        Medicamento m = new Medicamento("MED-100", "Ibuprofeno", "Ibuprofeno", 2.0, 20, 2028, false);
        m.reducirStock(5);
        assertEquals(15, m.getStock());
    }

    @Test
    void reducirStockMayorAlDisponibleLanzaExcepcion() {
        Medicamento m = new Medicamento("MED-101", "Paracetamol", "Paracetamol", 1.5, 3, 2028, false);
        assertThrows(IllegalStateException.class, () -> m.reducirStock(10));
        assertEquals(3, m.getStock());
    }

    @Test
    void stockNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Medicamento("MED-102", "Leche", "Leche", 4.5, -5, 2028, false));
    }

    @Test
    void estaVencidoDetectaUnAnioPasado() {
        Medicamento vencido = new Medicamento("MED-103", "Jarabe", "X", 6.0, 5, 2020, false);
        Medicamento vigente = new Medicamento("MED-104", "Jarabe", "X", 6.0, 5, 2030, false);
        assertTrue(vencido.estaVencido());
        assertFalse(vigente.estaVencido());
    }
}
