package com.boticasanfelipe.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DispensacionTest {
    private Medicamento crearMedicamento(String codigo, int stock, int anio, boolean receta) {
        return new Medicamento(codigo, "Medicina " + codigo, "Principio", 2.0, stock, anio, receta);
    }

    private Cliente crearCliente() {
        return new Cliente("Marco Salinas", "40011223", "941234567", "Av. Los Ángeles 452");
    }

    private QuimicoFarmaceutico crearQuimico() {
        return new QuimicoFarmaceutico("Ana Rojas", "45678912", "944112233", "QF-01", "CQFP-8821");
    }

    private Vendedor crearVendedor() {
        return new Vendedor("Luis Paredes", "41234567", "944556677", "VD-01");
    }

    @Test
    void calcularTotalSumaLosSubtotales() {
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        venta.agregarItem(crearMedicamento("MED-1", 50, 2028, false), 3);
        venta.agregarItem(crearMedicamento("MED-2", 50, 2028, false), 2);
        assertEquals(10.0, venta.calcularTotal(), 0.001);
    }

    @Test
    void sobrecargaAgregarItemSinCantidadAgregaUnaUnidad() {
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        Medicamento m = crearMedicamento("MED-3", 10, 2028, false);
        venta.agregarItem(m);
        venta.agregarItem(m, 2);
        assertEquals(6.0, venta.calcularTotal(), 0.001);
    }

    @Test
    void vendedorNoPuedeVenderMedicamentoConReceta() {
        Dispensacion venta = new Dispensacion(crearCliente(), crearVendedor());
        Medicamento conReceta = crearMedicamento("MED-4", 50, 2028, true);
        assertThrows(IllegalStateException.class, () -> venta.agregarItem(conReceta, 1));
    }

    @Test
    void medicamentoVencidoNoSeAgrega() {
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        Medicamento vencido = crearMedicamento("MED-5", 10, 2020, false);
        assertThrows(IllegalArgumentException.class, () -> venta.agregarItem(vencido, 1));
    }

    @Test
    void agregarElMismoMedicamentoRespetaElStockAcumulado() {
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        Medicamento m = crearMedicamento("MED-6", 5, 2028, false);
        venta.agregarItem(m, 3);
        assertThrows(IllegalStateException.class, () -> venta.agregarItem(m, 3));
    }

    @Test
    void confirmarDescuentaElStockYNoSePuedeConfirmarDosVeces() {
        Medicamento m = crearMedicamento("MED-7", 10, 2028, false);
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        venta.agregarItem(m, 2);
        venta.confirmar();
        assertEquals(8, m.getStock());
        assertEquals(Dispensacion.COMPLETADA, venta.getEstado());
        assertThrows(IllegalStateException.class, () -> venta.confirmar());
        assertEquals(8, m.getStock());
    }

    @Test
    void siElStockCambioNoSeDescuentaNadaAMedias() {
        Medicamento a = crearMedicamento("MED-8", 10, 2028, false);
        Medicamento b = crearMedicamento("MED-9", 10, 2028, false);
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        venta.agregarItem(a, 5);
        venta.agregarItem(b, 5);
        b.reducirStock(8);
        assertThrows(IllegalStateException.class, () -> venta.confirmar());
        assertEquals(10, a.getStock());
        assertEquals(2, b.getStock());
    }

    @Test
    void cancelarCambiaElEstadoYNoDescuentaStock() {
        Medicamento m = crearMedicamento("MED-10", 10, 2028, false);
        Dispensacion venta = new Dispensacion(crearCliente(), crearQuimico());
        venta.agregarItem(m, 4);
        venta.cancelar();
        assertEquals(Dispensacion.CANCELADA, venta.getEstado());
        assertEquals(10, m.getStock());
    }
}
