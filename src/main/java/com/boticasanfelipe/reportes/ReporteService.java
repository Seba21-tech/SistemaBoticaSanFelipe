package com.boticasanfelipe.reportes;

import com.boticasanfelipe.modelo.Dispensacion;
import com.boticasanfelipe.modelo.Medicamento;

import java.util.List;
import java.util.stream.Collectors;

public class ReporteService {
    private static final int UMBRAL_STOCK_BAJO = 10;

    public List<Medicamento> medicamentosConStockBajo(List<Medicamento> inventario) {
        return inventario.stream()
                .filter(m -> m.stockBajo(UMBRAL_STOCK_BAJO))
                .collect(Collectors.toList());
    }

    public List<String> nombresDeVencidos(List<Medicamento> inventario) {
        return inventario.stream()
                .filter(m -> m.estaVencido())
                .map(m -> m.getNombreComercial())
                .collect(Collectors.toList());
    }

    public double totalRecaudado(List<Dispensacion> dispensaciones) {
        return dispensaciones.stream()
                .filter(d -> d.getEstado().equals(Dispensacion.COMPLETADA))
                .map(d -> d.calcularTotal())
                .reduce(0.0, (a, b) -> a + b);
    }

    public double totalRecaudadoConBucle(List<Dispensacion> dispensaciones) {
        double total = 0.0;
        for (Dispensacion d : dispensaciones) {
            if (d.getEstado().equals(Dispensacion.COMPLETADA)) {
                total = total + d.calcularTotal();
            }
        }
        return total;
    }

    public String generarInventario(List<Medicamento> inventario) {
        if (inventario.isEmpty()) {
            return "No hay medicamentos registrados.\n";
        }
        String texto = "=== Inventario de medicamentos ===\n";
        for (Medicamento m : inventario) {
            String estado = "";
            if (m.estaVencido()) {
                estado = "  [VENCIDO]";
            } else if (m.stockBajo(UMBRAL_STOCK_BAJO)) {
                estado = "  [STOCK BAJO]";
            }
            String receta = "sin receta";
            if (m.isRequiereReceta()) {
                receta = "con receta";
            }
            texto = texto + m.getCodigo() + " | " + m.getNombreComercial()
                    + " | S/ " + String.format("%.2f", m.getPrecioUnitario())
                    + " | stock: " + m.getStock()
                    + " | vence: " + m.getAnioVencimiento()
                    + " | " + receta + estado + "\n";
        }
        return texto;
    }

    public String generarResumen(List<Medicamento> inventario, List<Dispensacion> dispensaciones) {
        List<Medicamento> stockBajo = medicamentosConStockBajo(inventario);
        List<String> vencidos = nombresDeVencidos(inventario);
        double total = totalRecaudado(dispensaciones);

        String texto = "=== Reporte Botica San Felipe ===\n";
        texto = texto + "Medicamentos con stock bajo (<" + UMBRAL_STOCK_BAJO + "): " + stockBajo.size() + "\n";
        for (Medicamento m : stockBajo) {
            texto = texto + "  - " + m + "\n";
        }
        texto = texto + "Medicamentos vencidos: " + vencidos.size() + "\n";
        for (String nombre : vencidos) {
            texto = texto + "  - " + nombre + "\n";
        }
        texto = texto + String.format("Total recaudado: S/ %.2f\n", total);
        return texto;
    }
}
