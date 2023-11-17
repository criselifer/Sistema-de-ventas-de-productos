package com.example.sistemadeventasdeproductos.producto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;
import com.example.sistemadeventasdeproductos.api.services.ProductoService;
import java.util.List;

public class NewProductoActivity extends AppCompatActivity {

    private EditText codigo;
    private EditText nombre;
    private Spinner spinnerCategoria;
    private Categoria categoriaSeleccionada;
    private EditText precioVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);

        CategoriaService categoriaService = CategoriaService.getInstance();
        List<Categoria> listaCategorias;
        ArrayAdapter<Categoria> adaptadorCategoria;

        // Configuraciones para spinnerCategoria
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        listaCategorias = categoriaService.obtenerCategorias();
        adaptadorCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adaptadorCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adaptadorCategoria);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoriaSeleccionada = (Categoria) parentView.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejo cuando no se ha seleccionado nada
            }
        });

        codigo = findViewById(R.id.txtCodigo);
        nombre = findViewById(R.id.txtNombre);
        precioVenta = findViewById(R.id.txtPrecio);

    }

    public void agregarNuevoProducto(View v) {

        Producto producto = new Producto();
        ProductoService productoService = ProductoService.getInstance();

        producto.setCategoria(categoriaSeleccionada);
        producto.setCodigo(codigo.getText().toString());
        producto.setNombre(nombre.getText().toString());
        producto.setPrecioVenta(Integer.parseInt(precioVenta.getText().toString()));

        productoService.agregarProducto(producto);

        Toast.makeText(
                NewProductoActivity.this,R.string.productoRegistradoCorrectamente,Toast.LENGTH_LONG).show();

        Intent newProductoIntent = new Intent(this, ProductoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("consulta","");
        newProductoIntent.putExtras(bundle);
        startActivity(newProductoIntent);
        finish();
    }

}