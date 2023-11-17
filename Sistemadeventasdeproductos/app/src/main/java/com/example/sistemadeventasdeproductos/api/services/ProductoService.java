package com.example.sistemadeventasdeproductos.api.services;

import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoService {
    private static ProductoService instanciaUnica;
    private List<Producto> productos;
    private Integer index;
    private ProductoService() {

        this.productos = new ArrayList<>();
        CategoriaService categoriaService = CategoriaService.getInstance();
        Categoria categoria;
        Producto producto;

        producto = new Producto();
        producto.setId(1);
        producto.setNombre("Gaseosa Coca Cola 500ml");
        producto.setCodigo("gaseosa1346coca500");

        categoria = categoriaService.categoriaById(1);
        producto.setCategoria(categoria);

        producto.setPrecioVenta(5000);
        this.productos.add(producto);

        this.index = 2;

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

    public List<Producto> productoByCodigo(String codigo) {
        List<Producto> producto = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                producto.add(productos.get(i));
            }
        }
        return producto;
    }

    public List<Producto> productosByCategoria(Integer idCategoria) {
        return productos.stream()
                .filter(producto -> producto.getCategoria().getId() == idCategoria)
                .collect(Collectors.toList());
    }

    public List<Producto> productoByCodigoYCategoria(String codigo, Integer idCategoria){

        List<Producto> productos = this.productosByCategoria(idCategoria);
        List<Producto> producto = new ArrayList<>();

        for (int i=0; i<productos.size(); i++){
            if (productos.get(i).getCodigo().equalsIgnoreCase(codigo)){
                producto.add(productos.get(i));
            }
        }

        return producto;

    }

}
