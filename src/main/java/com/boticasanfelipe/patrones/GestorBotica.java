package com.boticasanfelipe.patrones;

import com.boticasanfelipe.modelo.Cliente;
import com.boticasanfelipe.modelo.Dispensacion;
import com.boticasanfelipe.modelo.Medicamento;
import com.boticasanfelipe.modelo.PersonalFarmacia;

import java.util.ArrayList;

public class GestorBotica {
    private static GestorBotica instancia;

    private ArrayList<Medicamento> inventario = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<PersonalFarmacia> personal = new ArrayList<>();
    private ArrayList<Dispensacion> dispensaciones = new ArrayList<>();

    private GestorBotica() {
    }

    public static GestorBotica getInstancia() {
        if (instancia == null) {
            instancia = new GestorBotica();
        }
        return instancia;
    }

    public void registrarMedicamento(Medicamento medicamento) {
        inventario.add(medicamento);
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarPersonal(PersonalFarmacia personalFarmacia) {
        personal.add(personalFarmacia);
    }

    public void registrarDispensacion(Dispensacion dispensacion) {
        dispensaciones.add(dispensacion);
    }

    public ArrayList<Medicamento> getInventario() {
        return new ArrayList<>(inventario);
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public ArrayList<PersonalFarmacia> getPersonal() {
        return new ArrayList<>(personal);
    }

    public ArrayList<Dispensacion> getDispensaciones() {
        return new ArrayList<>(dispensaciones);
    }

    public Cliente buscarClientePorDni(String dni) {
        for (Cliente c : clientes) {
            if (c.coincideConDni(dni)) {
                return c;
            }
        }
        return null;
    }

    public PersonalFarmacia buscarPersonalPorCodigo(String codigo) {
        for (PersonalFarmacia p : personal) {
            if (p.getCodigoEmpleado().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Medicamento buscarMedicamentoPorCodigo(String codigo) {
        for (Medicamento m : inventario) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) {
                return m;
            }
        }
        return null;
    }

    public void limpiarTodo() {
        inventario.clear();
        clientes.clear();
        personal.clear();
        dispensaciones.clear();
    }
}
