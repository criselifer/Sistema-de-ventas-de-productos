package com.example.sistemadeventasdeproductos.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {

    private Integer id;
    private Cliente cliente;
    private Integer nroFactura;
    private Date fecha;
    private Integer total;
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(Integer nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
