package com.example.sistemadeventasdeproductos.producto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import com.example.sistemadeventasdeproductos.api.services.ProductoService;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import com.example.sistemadeventasdeproductos.venta.AdapterVenta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductoActivity extends AppCompatActivity {

    private RecyclerView rvProducto;
    private AdapterProducto adapterProducto;
    private FloatingActionButton fabNuevoProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        rvProducto = findViewById(R.id.rvListadoProductos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducto.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarProductos(filtro);

        fabNuevoProducto = findViewById(R.id.fabNuevoProducto);
        fabNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newProductoIntent = new Intent(ProductoActivity.this, NewProductoActivity.class);
                startActivity(newProductoIntent);
                finish();
            }
        });

    }

    public void cargarProductos(String filtro) {

        ProductoService productoService = ProductoService.getInstance();
        List<Producto> listaProductos = new ArrayList<>();

        if (filtro.equals("")) {
            listaProductos = productoService.obtenerProductos();
        } else {

        }

        adapterProducto = new AdapterProducto(listaProductos);
        rvProducto.setAdapter(adapterProducto);

    }

    public void eliminarProducto(View view) {

        ProductoService productoService = ProductoService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idProducto = Integer.parseInt( id.getText().toString() );

        productoService.eliminarProductoById( idProducto );

        Intent newProductoIntent = new Intent(this, ProductoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newProductoIntent.putExtras(bundle);
        startActivity(newProductoIntent);
        finish();

    }

}