package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;

public class FiltroVentaActivity extends AppCompatActivity {
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_venta);
    }

    public void btnEventoFiltrarVenta(View v) {

        Intent principalIntent = new Intent(this, VentaActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("consulta","");

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}