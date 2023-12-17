package com.example.sistemadeventasdeproductos.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {

    private Integer id;
    private Cliente proveedor;
    private Integer nroFactura;
    private Date fecha;
    private Integer total;
    private List<DetalleCompra> detalles;

    public Compra() {
        this.detalles = new ArrayList<DetalleCompra>();
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

    public Cliente getProveedor() {
        return this.proveedor;
    }

    public void setProveedor(Cliente proveedor) {
        this.proveedor = proveedor;
    }

    public void agregarDetalle(DetalleCompra detalleCompra) {
        this.detalles.add(detalleCompra);
    }
}
