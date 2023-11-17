package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.CategoriaService;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.example.sistemadeventasdeproductos.api.services.VentaService;

import java.util.List;

public class FiltroVentaActivity extends AppCompatActivity {
    Spinner spinnerCliente;
    Cliente clienteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_venta);

        ClienteService clienteService = ClienteService.getInstance();
        List<Cliente> listaClientes;
        ArrayAdapter<Cliente> adaptadorCliente;
        clienteSeleccionado = null;

        // Configuraciones para spinnerCategoria
        spinnerCliente = findViewById(R.id.spinnerCliente);
        listaClientes = clienteService.obtenerClientes();
        adaptadorCliente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        adaptadorCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adaptadorCliente);
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                clienteSeleccionado = (Cliente) parentView.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejo cuando no se ha seleccionado nada
            }
        });

    }

    public void btnEventoFiltrarVenta(View v) {

        Intent principalIntent = new Intent(this, VentaActivity.class);
        Bundle bundle = new Bundle();

        if (clienteSeleccionado == null) {
            bundle.putString("consulta", "");
        } else {
            bundle.putString("consulta", "/" + clienteSeleccionado.getId());
        }

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}