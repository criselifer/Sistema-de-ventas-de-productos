package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VentaActivity extends AppCompatActivity {

    private RecyclerView rvVenta;
    private AdapterVenta adapterVenta;
    private FloatingActionButton fabNuevaVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        rvVenta = findViewById(R.id.rvListadoVentas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvVenta.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarVentas(filtro);

        fabNuevaVenta=findViewById(R.id.fabNuevaVenta);
        fabNuevaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newVentaIntent = new Intent(VentaActivity.this, NewVentaActivity.class);
                startActivity(newVentaIntent);
                finish();
            }
        });

    }

    public void cargarVentas(String filtro) {

        VentaService ventaService = VentaService.getInstance();
        List<Venta> listaVentas = new ArrayList<>();

        if (filtro.equals("")) {
            listaVentas = ventaService.obtenerVentas();
        } else {
            String[] filtroSplit = filtro.split("/");
            listaVentas = ventaService.ventasByCliente(Integer.parseInt(filtroSplit[1]));
        }

        adapterVenta = new AdapterVenta(listaVentas);
        rvVenta.setAdapter(adapterVenta);

    }

    public void eliminarVenta(View view) {

        VentaService ventaService = VentaService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idVenta = Integer.parseInt( id.getText().toString() );

        ventaService.eliminarVentaById( idVenta );

        Intent newVentaIntent = new Intent(this, VentaActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newVentaIntent.putExtras(bundle);
        startActivity(newVentaIntent);
        finish();

    }

}