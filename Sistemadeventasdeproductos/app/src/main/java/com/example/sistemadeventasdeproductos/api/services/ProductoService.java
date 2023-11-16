package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private static ProductoService instanciaUnica;
    private List<Producto> productos;
    private Integer index;
    private ProductoService() {

        this.productos = new ArrayList<>();
        Producto producto;

        producto = new Producto();
        producto.setId(1);
        producto.setNombre("Gaseosa Coca Cola 500ml");
        producto.setCodigo("gaseosa1346coca500");
        producto.setCategoria(1);
        producto.setPrecioVenta(5000);
        this.productos.add(producto);

    }
    public static synchronized ProductoService getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new ProductoService();
        }
        return instanciaUnica;
    }
    public void agregarProducto(Producto producto) {
        producto.setId(this.index);
        this.productos.add(producto);
        this.index++;
    }
    public List<Producto> obtenerProductos() {
        return this.productos;
    }

    public void eliminarProductoById(int id) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getId() == id) {
                productos.remove(i);
                return;
            }
        }
    }

}
