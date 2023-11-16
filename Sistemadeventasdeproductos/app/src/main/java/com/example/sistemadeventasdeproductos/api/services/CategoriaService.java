package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.Categoria;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaService {

    private static CategoriaService instanciaUnica;
    private List<Categoria> listaCategorias;
    private Integer index;

    private CategoriaService() {

        this.listaCategorias = new ArrayList<>();
        Categoria categoria;

        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Bebida");
        this.listaCategorias.add(categoria);

        categoria = new Categoria();
        categoria.setId(2);
        categoria.setNombre("Platos");
        this.listaCategorias.add(categoria);

        categoria = new Categoria();
        categoria.setId(3);
        categoria.setNombre("Minutas");
        this.listaCategorias.add(categoria);

        this.index = 4;

    }

    public static synchronized CategoriaService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new CategoriaService();
        }
        return instanciaUnica;
    }

    public void agregarCategoria(Categoria categoria) {
        categoria.setId(this.index);
        this.listaCategorias.add(categoria);
        this.index++;
    }

    public List<Categoria> obtenerCategorias() {
        return this.listaCategorias;
    }

    public List<Categoria> categoriasByNombre(String nombre) {
        return listaCategorias.stream()
                .filter(categoria -> categoria.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public void eliminarCategoriaById(int id) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            Categoria categoria = listaCategorias.get(i);
            if (categoria.getId() == id) {
                listaCategorias.remove(i);
                return;
            }
        }
    }

}
