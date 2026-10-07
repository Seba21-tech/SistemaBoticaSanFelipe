package com.boticasanfelipe.patrones;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Persona;
import com.boticasanfelipe.modelo.PersonalFarmacia;
import com.boticasanfelipe.modelo.QuimicoFarmaceutico;
import com.boticasanfelipe.modelo.Vendedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatronesTest {
    @Test
    void laFabricaCreaElTipoCorrectoDePersona() {
        Persona cliente = FabricaPersonas.crearPersona("CLIENTE", "Ana", "11111111", "900", "Calle");
        Persona quimico = FabricaPersonas.crearPersona("QUIMICO", "Beto", "22222222", "900", "CQFP-1");
        Persona vendedor = FabricaPersonas.crearPersona("VENDEDOR", "Carla", "33333333", "900", null);
        assertTrue(cliente instanceof Cliente);
        assertTrue(quimico instanceof QuimicoFarmaceutico);
        assertTrue(vendedor instanceof Vendedor);
    }

    @Test
    void laFabricaLanzaExcepcionConTipoDesconocido() {
        assertThrows(IllegalArgumentException.class,
                () -> FabricaPersonas.crearPersona("ALIEN", "X", "11111111", "900", null));
    }

    @Test
    void elCodigoDeEmpleadoNoContieneElDni() {
        PersonalFarmacia p = (PersonalFarmacia) FabricaPersonas.crearPersona(
                "VENDEDOR", "Rosa", "55555555", "900", null);
        assertFalse(p.getCodigoEmpleado().contains("55555555"));
    }

    @Test
    void elSingletonSiempreDevuelveLaMismaInstancia() {
        assertSame(GestorBotica.getInstancia(), GestorBotica.getInstancia());
    }

    @Test
    void elGestorEncuentraClientesPorDniSinGuardarloEnTextoPlano() {
        GestorBotica gestor = GestorBotica.getInstancia();
        Cliente cliente = new Cliente("Luz Vega", "70112233", "900", "Calle 5");
        gestor.registrarCliente(cliente);
        assertSame(cliente, gestor.buscarClientePorDni("70112233"));
        assertNull(gestor.buscarClientePorDni("99999999"));
    }
}
