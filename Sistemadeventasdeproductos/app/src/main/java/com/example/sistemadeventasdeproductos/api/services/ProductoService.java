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

        producto = new Producto();
        producto.setId(2);
        producto.setNombre("Gaseosa Fanta 500ml");
        producto.setCodigo("gaseosa1346fanta500");
        categoria = categoriaService.categoriaById(1);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(5000);
        producto.setExistencia(0);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(3);
        producto.setNombre("Gaseosa Pepsi 500ml");
        producto.setCodigo("gaseosa1346pepsi500");
        categoria = categoriaService.categoriaById(1);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(4500);
        producto.setExistencia(2);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(4);
        producto.setNombre("Albondigas");
        producto.setCodigo("albondigas1346carne");
        categoria = categoriaService.categoriaById(2);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(16000);
        producto.setExistencia(1);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(5);
        producto.setNombre("Tallarín");
        producto.setCodigo("tallarin1346carnepollo");
        categoria = categoriaService.categoriaById(2);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(17000);
        producto.setExistencia(5);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(6);
        producto.setNombre("Milanesa");
        producto.setCodigo("milanesa1346carnepollo");
        categoria = categoriaService.categoriaById(2);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(19000);
        producto.setExistencia(0);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(7);
        producto.setNombre("Marinera");
        producto.setCodigo("marinera1346carnepollo");
        categoria = categoriaService.categoriaById(2);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(19000);
        producto.setExistencia(10);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(8);
        producto.setNombre("Empanada");
        producto.setCodigo("empanada1346varios");
        categoria = categoriaService.categoriaById(3);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(2500);
        producto.setExistencia(2);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(9);
        producto.setNombre("Sandwich");
        producto.setCodigo("sandwich1346varios");
        categoria = categoriaService.categoriaById(3);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(3500);
        producto.setExistencia(0);
        this.productos.add(producto);

        producto = new Producto();
        producto.setId(9);
        producto.setNombre("Croqueta");
        producto.setCodigo("croqueta1346pollocarne");
        categoria = categoriaService.categoriaById(3);
        producto.setCategoria(categoria);
        producto.setPrecioVenta(4000);
        producto.setExistencia(10);
        this.productos.add(producto);

        this.index = 11;

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

    public void actualizarStock(Integer idProducto, Integer cantidad) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getId() == idProducto) {
                producto.setExistencia(producto.getExistencia() - cantidad);
                return;
            }
        }
    }

    public Producto obtenerProducto(Integer idProducto) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getId() == idProducto) {
                return producto;
            }
        }
        return null;
    }

    public ArrayList<String> getNombres() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Producto producto : productos) {
            nombres.add(producto.getNombre());
        }
        return nombres;
    }

    public ArrayList<String> getCodigos() {
        ArrayList<String> codigos = new ArrayList<>();
        for (Producto producto : productos) {
            codigos.add(producto.getCodigo());
        }
        return codigos;
    }

}
