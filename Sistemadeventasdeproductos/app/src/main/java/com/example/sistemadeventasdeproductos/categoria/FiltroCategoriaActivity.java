package com.example.sistemadeventasdeproductos.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;

public class FiltroCategoriaActivity extends AppCompatActivity {

    String value;
    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_categoria);
        nombre = findViewById(R.id.txtNombre);
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