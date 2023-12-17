package com.example.sistemadeventasdeproductos.compra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Compra;
import com.example.sistemadeventasdeproductos.api.services.CompraService;
import com.example.sistemadeventasdeproductos.venta.AdapterVentas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CompraActivity extends AppCompatActivity {


    private RecyclerView rvCompra;
    private AdapterCompra adapterCompra;
    private FloatingActionButton fabNuevaCompra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        // Verificar y solicitar permisos si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        rvCompra = findViewById(R.id.rvListadoCompras);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCompra.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarCompras(filtro);

        fabNuevaCompra=findViewById(R.id.fabNuevaCompra);
        fabNuevaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCompraIntent = new Intent(CompraActivity.this, NewCompraActivity.class);
                startActivity(newCompraIntent);
                finish();
            }
        });

    }

    public void cargarCompras(String filtro) {

        CompraService compraService = CompraService.getInstance();
        List<Compra> listaCompras = new ArrayList<>();

        if (filtro.equals("")) {
            listaCompras = compraService.obtenerCompras();
        } else {
            String[] filtroSplit = filtro.split("/");
            listaCompras = compraService.comprasByProveedor(Integer.parseInt(filtroSplit[1]));
        }

        adapterCompra = new AdapterCompra(listaCompras);
        rvCompra.setAdapter(adapterCompra);

    }

    public void eliminarCompra(View view) {

        CompraService compraService = CompraService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idCompra = Integer.parseInt( id.getText().toString() );

        compraService.eliminarCompraById( idCompra );

        Intent newCompraIntent = new Intent(this, CompraActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newCompraIntent.putExtras(bundle);
        startActivity(newCompraIntent);
        finish();

    }



    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}