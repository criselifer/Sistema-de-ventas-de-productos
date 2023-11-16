package com.example.sistemadeventasdeproductos.api.models;

public class Categoria {

    private Integer id;

    private String nombre;

    public Categoria() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String descripcion) {
        this.nombre = descripcion;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
