package com.boticasanfelipe.reportes;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Dispensacion;
import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.modelo.QuimicoFarmaceutico;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReporteServiceTest {
    private ReporteService reportes = new ReporteService();

    private Medicamento crearMedicamento(String codigo, int stock, int anio) {
        return new Medicamento(codigo, "Medicina " + codigo, "Principio", 2.0, stock, anio, false);
    }

    private Dispensacion crearVenta() {
        Cliente cliente = new Cliente("Marco Salinas", "40011223", "941234567", "Av. Los Ángeles 452");
        QuimicoFarmaceutico quimico = new QuimicoFarmaceutico("Ana Rojas", "45678912", "944112233", "QF-01", "CQFP-8821");
        return new Dispensacion(cliente, quimico);
    }

    @Test
    void filterSeleccionaSoloLosMedicamentosConStockBajo() {
        ArrayList<Medicamento> inventario = new ArrayList<>();
        inventario.add(crearMedicamento("A", 3, 2028));
        inventario.add(crearMedicamento("B", 50, 2028));
        assertEquals(1, reportes.medicamentosConStockBajo(inventario).size());
    }

    @Test
    void filterYMapObtienenLosNombresDeLosVencidos() {
        ArrayList<Medicamento> inventario = new ArrayList<>();
        inventario.add(crearMedicamento("A", 30, 2020));
        inventario.add(crearMedicamento("B", 30, 2030));
        List<String> nombres = reportes.nombresDeVencidos(inventario);
        assertEquals(1, nombres.size());
        assertEquals("Medicina A", nombres.get(0));
    }

    @Test
    void reduceDaElMismoTotalQueElBucle() {
        Dispensacion venta = crearVenta();
        venta.agregarItem(crearMedicamento("C", 50, 2028), 4);
        venta.confirmar();
        ArrayList<Dispensacion> ventas = new ArrayList<>();
        ventas.add(venta);
        assertEquals(8.0, reportes.totalRecaudado(ventas), 0.001);
        assertEquals(reportes.totalRecaudadoConBucle(ventas), reportes.totalRecaudado(ventas), 0.001);
    }

    @Test
    void elTotalExcluyeVentasPendientesYCanceladas() {
        Medicamento m = crearMedicamento("D", 50, 2028);

        Dispensacion completada = crearVenta();
        completada.agregarItem(m, 1);
        completada.confirmar();

        Dispensacion pendiente = crearVenta();
        pendiente.agregarItem(m, 10);

        Dispensacion cancelada = crearVenta();
        cancelada.agregarItem(m, 10);
        cancelada.cancelar();

        ArrayList<Dispensacion> ventas = new ArrayList<>();
        ventas.add(completada);
        ventas.add(pendiente);
        ventas.add(cancelada);
        assertEquals(2.0, reportes.totalRecaudado(ventas), 0.001);
    }

    @Test
    void elInventarioListaTodosLosMedicamentosYMarcaVencidosYStockBajo() {
        ArrayList<Medicamento> inventario = new ArrayList<>();
        inventario.add(crearMedicamento("A", 3, 2028));
        inventario.add(crearMedicamento("B", 50, 2020));
        inventario.add(crearMedicamento("C", 50, 2028));
        String texto = reportes.generarInventario(inventario);
        assertTrue(texto.contains("A | Medicina A"));
        assertTrue(texto.contains("[STOCK BAJO]"));
        assertTrue(texto.contains("[VENCIDO]"));
        assertTrue(texto.contains("C | Medicina C"));
    }

    @Test
    void elInventarioVacioAvisaQueNoHayMedicamentos() {
        assertEquals("No hay medicamentos registrados.\n", reportes.generarInventario(new ArrayList<Medicamento>()));
    }
}
