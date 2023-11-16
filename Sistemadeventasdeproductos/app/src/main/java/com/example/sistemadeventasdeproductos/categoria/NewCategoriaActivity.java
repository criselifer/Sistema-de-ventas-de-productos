package com.example.sistemadeventasdeproductos.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;

public class NewCategoriaActivity extends AppCompatActivity {

    private EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_categoria);
        nombre = findViewById(R.id.txtNombreNuevo);
    }

    public void agregarNuevaCategoria(View v) {

        Categoria categoria = new Categoria();
        CategoriaService categoriaService = CategoriaService.getInstance();
        categoria.setNombre(nombre.getText().toString());
        categoriaService.agregarCategoria(categoria);

        Toast.makeText(
                NewCategoriaActivity.this,R.string.categoriaAgregadaCorrectamente,Toast.LENGTH_LONG).show();

        Intent newCategoriaIntent = new Intent(this, CategoriasActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newCategoriaIntent.putExtras(bundle);
        startActivity(newCategoriaIntent);
        finish();
    }

}