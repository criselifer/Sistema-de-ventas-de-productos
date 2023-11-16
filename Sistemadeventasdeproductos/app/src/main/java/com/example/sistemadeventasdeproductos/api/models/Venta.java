package com.example.sistemadeventasdeproductos.api.models;

import java.util.Date;

public class Venta {

    private Integer id;
    private Integer nroFactura;
    private Date fecha;
    private Integer total;

    public Venta() {
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

}
