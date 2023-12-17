package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.DetalleCompra;
import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;

import java.util.ArrayList;
import java.util.List;

public class DetalleCompraService {

    private static DetalleCompraService instanciaUnica;
    private List<DetalleCompra> detalleCompras;
    private Integer index;
    private DetalleCompraService() {
        this.detalleCompras = new ArrayList<>();
        this.index = 1;
    }
    public static synchronized DetalleCompraService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new DetalleCompraService();
        }
        return instanciaUnica;
    }
    public void agregarDetalleCompra(DetalleCompra detalleCompra) {
        detalleCompra.setId(this.index);
        this.detalleCompras.add(detalleCompra);
        this.index++;
    }
    public List<DetalleCompra> obtenerDetallesCompra() {
        return this.detalleCompras;
    }

    public void eliminarDetalleCompraById(int id) {
        for (int i = 0; i < detalleCompras.size(); i++) {
            DetalleCompra detalleCompra = detalleCompras.get(i);
            if (detalleCompra.getId() == id) {
                detalleCompras.remove(i);
                return;
            }
        }
    }
}
