package com.example.sistemadeventasdeproductos.producto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;
import com.example.sistemadeventasdeproductos.api.services.ProductoService;

import java.util.ArrayList;
import java.util.List;

public class FiltroProductoActivity extends AppCompatActivity {
    AutoCompleteTextView codigo;
    Spinner spinnerCategoria;
    Categoria categoriaSeleccionada;
    CheckBox checkBoxCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_producto);

        ProductoService productoService = ProductoService.getInstance();
        ArrayList<String> codigos = productoService.getCodigos();
        codigo = findViewById(R.id.autoCompleteTextViewCodigo);
        ArrayAdapter<String> adapterCodigos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, codigos);
        codigo.setAdapter(adapterCodigos);

        categoriaSeleccionada = null;

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

        checkBoxCategoria = findViewById(R.id.checkBoxCategoria);

    }

    public void btnEventoFiltrarProducto(View v) {

        Intent principalIntent = new Intent(this, ProductoActivity.class);
        Bundle bundle = new Bundle();

        if(codigo.getText().toString().equalsIgnoreCase("") && !checkBoxCategoria.isChecked()) {
            bundle.putString("consulta", "");
        } else if (!codigo.getText().toString().equalsIgnoreCase("") && !checkBoxCategoria.isChecked()) {
            bundle.putString("consulta", "codigo/" + codigo.getText().toString());
        } else if (codigo.getText().toString().equalsIgnoreCase("") && checkBoxCategoria.isChecked()) {
            bundle.putString("consulta", "categoria/" + categoriaSeleccionada.getId());
        } else {
            bundle.putString("consulta", "codigo-categoria/" + codigo.getText().toString() + "/" + categoriaSeleccionada.getId());
        }

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}