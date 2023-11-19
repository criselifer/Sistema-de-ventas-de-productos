package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentaService {
    private static VentaService instanciaUnica;
    private List<Venta> ventas;
    private Integer index;
    private int indexDetalles;

    private VentaService() {
        this.ventas = new ArrayList<>();
        this.index = 1;
    }
    public static synchronized VentaService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new VentaService();
        }
        return instanciaUnica;
    }
    public void agregarVenta(Venta venta) {
        venta.setId(this.index);
        this.ventas.add(venta);
        this.index++;
    }

    public void agregarDetalleAVenta(int idVenta, DetalleVenta detalleVenta) {
        Venta venta = obtenerVentaById(idVenta);
        if (venta != null) {
            detalleVenta.setId(this.indexDetalles++);
            venta.agregarDetalle(detalleVenta);
        }
    }

    private Venta obtenerVentaById(int id) {
        for (Venta venta : ventas) {
            if (venta.getId() == id) {
                return venta;
            }
        }
        return null;
    }

    public List<Venta> obtenerVentas() {
        return this.ventas;
    }

    public void eliminarVentaById(int id) {
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            if (venta.getId() == id) {
                ventas.remove(i);
                return;
            }
        }
    }

    public List<Venta> ventasByCliente(Integer idCliente) {
        return ventas.stream()
                .filter(venta -> venta.getCliente().getId() == idCliente)
                .collect(Collectors.toList());
    }

}
