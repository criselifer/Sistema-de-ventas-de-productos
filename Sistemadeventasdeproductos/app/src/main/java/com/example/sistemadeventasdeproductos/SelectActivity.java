package com.example.sistemadeventasdeproductos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.sistemadeventasdeproductos.categoria.FiltroCategoriaActivity;
import com.example.sistemadeventasdeproductos.cliente.FiltroClienteActivity;
import com.example.sistemadeventasdeproductos.venta.FiltroVentaActivity;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void btnEventoCategoria(View v) {
        Intent principalIntent=new Intent(this, FiltroCategoriaActivity.class);
        startActivity(principalIntent);
    }

    public void btnEventoProducto(View v) {
        Intent principalIntent=new Intent(this, FiltroCategoriaActivity.class);
        /*Bundle bundle=new Bundle();
        bundle.putString("user",getIntent().getExtras().getString("user"));
        principalIntent.putExtras(bundle);*/
        startActivity(principalIntent);
    }

    public void btnEventoCliente(View v) {
        Intent principalIntent  =new Intent(this, FiltroClienteActivity.class);
        /*Bundle bundle=new Bundle();
        bundle.putString("user",getIntent().getExtras().getString("user"));
        principalIntent.putExtras(bundle);*/
        startActivity(principalIntent);
    }

    public void btnEventoVenta(View v) {
        Intent principalIntent = new Intent(this, FiltroVentaActivity.class);
        /*Bundle bundle=new Bundle();
        bundle.putString("user",getIntent().getExtras().getString("user"));
        principalIntent.putExtras(bundle);*/
        startActivity(principalIntent);
    }

}