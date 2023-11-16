package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteService {
    private static ClienteService instanciaUnica;
    private List<Cliente> clientes;
    private Integer index;

    private ClienteService() {

        this.clientes = new ArrayList<>();
        Cliente cliente;

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setEmail("juan.perez@example.com");
        cliente.setRuc("123456789");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(2);
        cliente.setNombre("María");
        cliente.setApellido("González");
        cliente.setEmail("maria.gonzalez@example.com");
        cliente.setRuc("987654321");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(3);
        cliente.setNombre("Carlos");
        cliente.setApellido("Martínez");
        cliente.setEmail("carlos.martinez@example.com");
        cliente.setRuc("555123456");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(4);
        cliente.setNombre("Ana");
        cliente.setApellido("López");
        cliente.setEmail("ana.lopez@example.com");
        cliente.setRuc("333789012");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(5);
        cliente.setNombre("Pedro");
        cliente.setApellido("Ramírez");
        cliente.setEmail("pedro.ramirez@example.com");
        cliente.setRuc("111234567");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(6);
        cliente.setNombre("Sabino");
        cliente.setApellido("Ramírez");
        cliente.setEmail("pedro.ramirez@example.com");
        cliente.setRuc("111234567");
        this.clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId(7);
        cliente.setNombre("María");
        cliente.setApellido("Ramírez");
        cliente.setEmail("pedro.ramirez@example.com");
        cliente.setRuc("111234567");
        this.clientes.add(cliente);

        this.index = 8;

    }

    public static synchronized ClienteService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new ClienteService();
        }
        return instanciaUnica;
    }

    public void agregarCliente(Cliente cliente) {
        cliente.setId(this.index);
        this.clientes.add(cliente);
        this.index++;
    }

    public List<Cliente> obtenerClientes() {
        return this.clientes;
    }

    public List<Cliente> clientesByNombre(String nombre) {
        return clientes.stream()
                .filter(cliente -> cliente.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public List<Cliente> clientesByApellido(String apellido) {
        return clientes.stream()
                .filter(cliente -> cliente.getApellido().equalsIgnoreCase(apellido))
                .collect(Collectors.toList());
    }

    public List<Cliente> clienteByRuc(String ruc) {
        return clientes.stream()
                .filter(cliente -> cliente.getRuc().equalsIgnoreCase(ruc))
                .collect(Collectors.toList());
    }

    public List<Cliente> clientesByNombreYApellido(String nombre, String apellido) {
        return clientes.stream()
                .filter(cliente ->
                        cliente.getNombre().equalsIgnoreCase(nombre) &&
                                cliente.getApellido().equalsIgnoreCase(apellido))
                .collect(Collectors.toList());
    }

    public void eliminarClienteById(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente.getId() == id) {
                clientes.remove(i);
                return;
            }
        }
    }

}