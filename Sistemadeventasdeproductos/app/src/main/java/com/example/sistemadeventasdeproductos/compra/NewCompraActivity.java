package com.example.sistemadeventasdeproductos.compra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.models.Compra;
import com.example.sistemadeventasdeproductos.api.models.DetalleCompra;
import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.example.sistemadeventasdeproductos.api.services.CompraService;
import com.example.sistemadeventasdeproductos.api.services.DetalleCompraService;
import com.example.sistemadeventasdeproductos.api.services.DetalleVentaService;
import com.example.sistemadeventasdeproductos.api.services.ProductoService;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import com.example.sistemadeventasdeproductos.venta.VentaActivity;

import java.util.Date;
import java.util.List;


public class NewCompraActivity extends AppCompatActivity {

    private Cliente proveedorSeleccionado;
    private Spinner spinnerCliente;
    private Spinner spinnerProducto;
    private Producto productoSeleccionado;
    private EditText nroFactura;
    //private EditText fecha;
    private TextView total;
    private EditText cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compra);

        cantidad = findViewById(R.id.txtCantidadProducto);

        ClienteService clienteService = ClienteService.getInstance();
        List<Cliente> listaClientes;
        ArrayAdapter<Cliente> adaptadorCliente;

        // Configuraciones para spinnerCliente
        spinnerCliente = findViewById(R.id.spinnerProveedor);
        listaClientes = clienteService.obtenerClientes();
        adaptadorCliente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        adaptadorCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adaptadorCliente);
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                proveedorSeleccionado = (Cliente) parentView.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejo cuando no se ha seleccionado nada
            }
        });

        // Configuraciones para el spinnerProducto
        ProductoService productoService = ProductoService.getInstance();
        List<Producto> listaProductos;
        ArrayAdapter<Producto> adaptadorProducto;

        spinnerProducto = findViewById(R.id.spinnerProducto);
        listaProductos = productoService.obtenerProductos();
        adaptadorProducto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaProductos);
        adaptadorProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adaptadorProducto);
        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                productoSeleccionado = (Producto) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejo cuando no se ha seleccionado nada
            }
        });

        nroFactura = findViewById(R.id.txtNroFactura);
        //fecha = findViewById(R.id.txtFecha);
        total = findViewById(R.id.txtTotal);

    }

    public void agregarNuevaCompra(View v) {

        Compra compra = new Compra();
        CompraService compraService = CompraService.getInstance();

        compra.setProveedor(proveedorSeleccionado);
        compra.setNroFactura(Integer.parseInt(nroFactura.getText().toString()));
        compra.setFecha(new Date());
        try {
            compra.setTotal(Integer.parseInt(total.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            compra.setTotal(0);
        }
        int cantidadD = Integer.parseInt(cantidad.getText().toString());
        productoSeleccionado.setExistencia(cantidadD+productoSeleccionado.getExistencia());
        DetalleCompra detalleCompra = new DetalleCompra();
        DetalleCompraService detalleCompraService = DetalleCompraService.getInstance();

        detalleCompra.setCantidad(cantidadD);
        detalleCompra.setIdProducto(productoSeleccionado.getId());
        detalleCompra.setSubtotal(productoSeleccionado.getPrecioVenta()*cantidadD);
        detalleCompraService.agregarDetalleCompra(detalleCompra);

        compra.setTotal(productoSeleccionado.getPrecioVenta()*cantidadD);
        compra.agregarDetalle(detalleCompra);
        compraService.agregarCompra(compra);


        Toast.makeText(
                com.example.sistemadeventasdeproductos.compra.NewCompraActivity.this,R.string.compraAgregadoCorrectamente,Toast.LENGTH_LONG).show();

        Intent newCompraIntent = new Intent(this, CompraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("consulta","");
        newCompraIntent.putExtras(bundle);
        startActivity(newCompraIntent);
        finish();
    }

}