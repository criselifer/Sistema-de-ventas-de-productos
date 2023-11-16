package com.example.sistemadeventasdeproductos.categoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.Objects;

public class CategoriasActivity extends AppCompatActivity {

    private RecyclerView rvCategorias;
    private AdapterCategoria adapterCategoria;
    private FloatingActionButton fabNuevaCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        rvCategorias = findViewById(R.id.rvListadoCategorias);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCategorias.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarCategorias(filtro);

        fabNuevaCategoria=findViewById(R.id.fabNuevaCategoria);
        fabNuevaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCategoriaIntent = new Intent(CategoriasActivity.this, NewCategoriaActivity.class);
                startActivity(newCategoriaIntent);
                finish();
            }
        });

    }

    public void cargarCategorias(String filtro) {

        CategoriaService categoriaService = CategoriaService.getInstance();

        if (filtro.equalsIgnoreCase("")) {
            List<Categoria> listaCategorias = categoriaService.obtenerCategorias();
            adapterCategoria = new AdapterCategoria(listaCategorias);
            rvCategorias.setAdapter(adapterCategoria);
        } else {
            List<Categoria> listaCategorias = categoriaService.categoriasByNombre(filtro.toString());
            adapterCategoria = new AdapterCategoria(listaCategorias);
            rvCategorias.setAdapter(adapterCategoria);
        }

    }

    public void eliminarCategoria(View view) {

        CategoriaService categoriaService = CategoriaService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idCategoria = Integer.parseInt( id.getText().toString() );

        categoriaService.eliminarCategoriaById( idCategoria );

        Intent newCategoriaIntent = new Intent(this, CategoriasActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newCategoriaIntent.putExtras(bundle);
        startActivity(newCategoriaIntent);
        finish();

    }

}