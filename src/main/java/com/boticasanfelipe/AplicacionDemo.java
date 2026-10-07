package com.boticasanfelipe;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Dispensacion;
import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.modelo.Persona;
import com.boticasanfelipe.modelo.PersonalFarmacia;
import com.boticasanfelipe.patrones.FabricaPersonas;
import com.boticasanfelipe.patrones.GestorBotica;
import com.boticasanfelipe.reportes.ReporteService;

public class AplicacionDemo {
    public static void main(String[] args) {
        GestorBotica gestor = GestorBotica.getInstancia();

        Persona quimica = FabricaPersonas.crearPersona(
                "QUIMICO", "Ana Rojas Vega", "45678912", "944112233", "CQFP-8821");
        Persona vendedor = FabricaPersonas.crearPersona(
                "VENDEDOR", "Luis Paredes", "41234567", "944556677", null);
        Cliente cliente = (Cliente) FabricaPersonas.crearPersona(
                "CLIENTE", "Marco Salinas", "40011223", "941234567", "Av. Los Ángeles 452");

        gestor.registrarPersonal((PersonalFarmacia) quimica);
        gestor.registrarPersonal((PersonalFarmacia) vendedor);
        gestor.registrarCliente(cliente);

        System.out.println(quimica);
        System.out.println(vendedor);
        System.out.println(cliente);

        Medicamento paracetamol = new Medicamento("MED-001", "Paracetamol 500mg", "Paracetamol",
                1.50, 8, 2028, false);
        Medicamento amoxicilina = new Medicamento("MED-002", "Amoxicilina 500mg", "Amoxicilina",
                0.80, 40, 2027, true);
        Medicamento jarabeVencido = new Medicamento("MED-003", "Jarabe Tos Seca", "Dextrometorfano",
                6.00, 15, 2025, false);

        gestor.registrarMedicamento(paracetamol);
        gestor.registrarMedicamento(amoxicilina);
        gestor.registrarMedicamento(jarabeVencido);

        Dispensacion venta1 = new Dispensacion(cliente, (PersonalFarmacia) vendedor);
        try {
            venta1.agregarItem(paracetamol, 2);
            venta1.confirmar();
            gestor.registrarDispensacion(venta1);
            System.out.printf("Venta 1 confirmada. Total: S/ %.2f%n", venta1.calcularTotal());
        } catch (IllegalStateException e) {
            System.out.println("No se pudo completar la venta 1: " + e.getMessage());
        } finally {
            System.out.println("-- Fin del intento de venta 1 --");
        }

        Dispensacion venta2 = new Dispensacion(cliente, (PersonalFarmacia) vendedor);
        try {
            venta2.agregarItem(amoxicilina, 10);
            venta2.confirmar();
            gestor.registrarDispensacion(venta2);
        } catch (IllegalStateException e) {
            System.out.println("Excepción esperada capturada: " + e.getMessage());
        } finally {
            System.out.println("-- Fin del intento de venta 2 --");
        }

        Dispensacion venta3 = new Dispensacion(cliente, (PersonalFarmacia) quimica);
        try {
            venta3.agregarItem(amoxicilina, 10);
            venta3.confirmar();
            gestor.registrarDispensacion(venta3);
            System.out.printf("Venta 3 confirmada. Total: S/ %.2f%n", venta3.calcularTotal());
        } catch (IllegalStateException e) {
            System.out.println("No se pudo completar la venta 3: " + e.getMessage());
        } finally {
            System.out.println("-- Fin del intento de venta 3 --");
        }

        ReporteService reportes = new ReporteService();
        System.out.println();
        System.out.println(reportes.generarResumen(gestor.getInventario(), gestor.getDispensaciones()));
    }
}
