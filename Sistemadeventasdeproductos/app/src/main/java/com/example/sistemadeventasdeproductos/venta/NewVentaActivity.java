package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.models.DetalleVenta;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.example.sistemadeventasdeproductos.api.services.DetalleVentaService;
import com.example.sistemadeventasdeproductos.api.services.ProductoService;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import java.util.Date;
import java.util.List;

public class NewVentaActivity extends AppCompatActivity {

    private Cliente clienteSeleccionado;
    private Spinner spinnerCliente;
    private Spinner spinnerProducto;
    private Producto productoSeleccionado;
    private EditText nroFactura;
    private TextView total;
    private EditText cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_venta);

        nroFactura = findViewById(R.id.txtNroFactura);
        total = findViewById(R.id.txtTotal);
        cantidad = findViewById(R.id.txtCantidadProducto);
        cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Este método se llama para notificar que algo está a punto de cambiar
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Este método se llama para notificar que el texto ha cambiado
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Este método se llama después de que el texto ha cambiado
                ProductoService productoService = ProductoService.getInstance();
                if (productoSeleccionado != null) {
                    Producto producto = productoService.obtenerProducto(productoSeleccionado.getId());
                    if (!editable.toString().isEmpty()) {
                        int cantidadIngresada = Integer.parseInt(editable.toString());
                        if (producto.getExistencia() < cantidadIngresada) {
                            Toast.makeText(
                                    NewVentaActivity.this, R.string.noHaySuficienteStock, Toast.LENGTH_LONG).show();
                            total.setText("");
                        } else {
                            total.setText(String.valueOf("Total : " + String.valueOf(producto.getPrecioVenta() * cantidadIngresada)));
                        }
                    } else {
                        total.setText("");
                    }
                } else {
                    Toast.makeText(
                            NewVentaActivity.this, R.string.seleccioneProducto, Toast.LENGTH_LONG).show();
                }
            }
        });

        ClienteService clienteService = ClienteService.getInstance();
        List<Cliente> listaClientes;
        ArrayAdapter<Cliente> adaptadorCliente;

        // Configuraciones para spinnerCliente
        spinnerCliente = findViewById(R.id.spinnerCliente);
        listaClientes = clienteService.obtenerClientes();
        adaptadorCliente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        adaptadorCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adaptadorCliente);
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                clienteSeleccionado = (Cliente) parentView.getItemAtPosition(position);
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

    }

    public void agregarNuevaVenta(View v) {

        VentaService ventaService = VentaService.getInstance();
        ProductoService productoService = ProductoService.getInstance();
        DetalleVentaService detalleVentaService = DetalleVentaService.getInstance();

        Venta venta = new Venta();
        venta.setCliente(clienteSeleccionado);
        venta.setNroFactura(Integer.parseInt(nroFactura.getText().toString()));
        venta.setFecha(new Date());
        try {
            venta.setTotal(Integer.parseInt(total.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            venta.setTotal(0);
        }
        int cantidadD = Integer.parseInt(cantidad.getText().toString());

        DetalleVenta detalleVenta = new DetalleVenta();
        if (productoSeleccionado.getExistencia() < cantidadD) {
            Toast.makeText(
                    NewVentaActivity.this,R.string.noHaySuficienteStock,Toast.LENGTH_LONG).show();
            return;
        }else {
            detalleVenta.setCantidad(cantidadD);
            detalleVenta.setIdProducto(productoSeleccionado.getId());
            detalleVenta.setSubtotal(productoSeleccionado.getPrecioVenta()*cantidadD);
            detalleVentaService.agregarReservaDetalleVenta(detalleVenta);
            venta.setTotal(productoSeleccionado.getPrecioVenta()*cantidadD);
            venta.agregarDetalle(detalleVenta);
            ventaService.agregarVenta(venta);

            productoService.actualizarStock(productoSeleccionado.getId(), cantidadD);

            Toast.makeText(
                    NewVentaActivity.this,R.string.ventaAgregadoCorrectamente,Toast.LENGTH_LONG).show();

            Intent newVentaIntent = new Intent(this, VentaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("consulta","");
            newVentaIntent.putExtras(bundle);
            startActivity(newVentaIntent);
            finish();
        }

    }

}