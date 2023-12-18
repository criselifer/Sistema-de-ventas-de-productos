package com.example.sistemadeventasdeproductos.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;
import java.util.ArrayList;

public class FiltroCategoriaActivity extends AppCompatActivity {

    String value;
    AutoCompleteTextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_categoria);

        CategoriaService categoriaService = CategoriaService.getInstance();
        ArrayList<String> nombres = categoriaService.getNombres();
        nombre = findViewById(R.id.autoCompleteTextViewNombre);
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres);
        nombre.setAdapter(adapterNombres);

    }

    public void btnEventoFiltrarCategoria(View v) {

        Intent principalIntent = new Intent(this, CategoriasActivity.class);
        Bundle bundle = new Bundle();

        if(nombre.getText().toString().equals("")){
            bundle.putString("consulta","");
        }else {
            bundle.putString("consulta",nombre.getText().toString());
        }

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}