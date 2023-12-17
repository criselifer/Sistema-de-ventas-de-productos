package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.Compra;
import com.example.sistemadeventasdeproductos.api.models.DetalleCompra;
import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;
import com.example.sistemadeventasdeproductos.api.models.Venta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompraService {

    private static CompraService instanciaUnica;
    private List<Compra> compras;
    private Integer index;
    private int indexDetalles;

    private CompraService() {
        this.compras = new ArrayList<>();
        this.index = 1;
    }
    public static synchronized CompraService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new CompraService();
        }
        return instanciaUnica;
    }
    public void agregarVenta(Compra compra) {
        compra.setId(this.index);
        this.compras.add(compra);
        this.index++;
    }

    public void agregarDetalleACompra(int idCompra, DetalleCompra detalleCompra) {
        Compra compra = obtenerCompraById(idCompra);
        if (compra != null) {
            detalleCompra.setId(this.indexDetalles++);
            compra.agregarDetalle(detalleCompra);
        }
    }

    private Compra obtenerCompraById(int id) {
        for (Compra compra : compras) {
            if (compra.getId() == id) {
                return compra;
            }
        }
        return null;
    }

    public List<Compra> obtenerCompras() {
        return this.compras;
    }

    public void eliminarCompraById(int id) {
        for (int i = 0; i < compras.size(); i++) {
            Compra compra = compras.get(i);
            if (compra.getId() == id) {
                compras.remove(i);
                return;
            }
        }
    }

    public List<Compra> comprasByCliente(Integer idProveedor) {
        return compras.stream()
                .filter(compra -> compra.getProveedor().getId() == idProveedor)
                .collect(Collectors.toList());
    }
}
