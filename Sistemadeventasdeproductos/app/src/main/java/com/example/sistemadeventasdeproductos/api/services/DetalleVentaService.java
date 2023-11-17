package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaService {

    private static DetalleVentaService instanciaUnica;
    private List<DetalleVenta> detalleVentas;
    private Integer index;
    private DetalleVentaService() {
        this.detalleVentas = new ArrayList<>();
        this.index = 1;
    }
    public static synchronized DetalleVentaService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new DetalleVentaService();
        }
        return instanciaUnica;
    }
    public void agregarReservaDetalleVenta(DetalleVenta detalleVenta) {
        detalleVenta.setId(this.index);
        this.detalleVentas.add(detalleVenta);
        this.index++;
    }
    public List<DetalleVenta> obtenerDetallesVentas() {
        return this.detalleVentas;
    }

    public void eliminarDetalleVentaById(int id) {
        for (int i = 0; i < detalleVentas.size(); i++) {
            DetalleVenta detalleVenta = detalleVentas.get(i);
            if (detalleVenta.getId() == id) {
                detalleVentas.remove(i);
                return;
            }
        }
    }

}
