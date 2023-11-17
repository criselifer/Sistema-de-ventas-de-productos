package com.example.sistemadeventasdeproductos.producto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.venta.VentaActivity;

public class FiltroProductoActivity extends AppCompatActivity {
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_producto);
    }

    public void btnEventoFiltrarProducto(View v) {

        Intent principalIntent = new Intent(this, ProductoActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("consulta","");

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}