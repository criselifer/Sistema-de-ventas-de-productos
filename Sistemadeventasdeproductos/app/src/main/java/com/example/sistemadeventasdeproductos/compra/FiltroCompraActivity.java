package com.example.sistemadeventasdeproductos.compra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.example.sistemadeventasdeproductos.compra.CompraActivity;

import java.util.List;

public class FiltroCompraActivity extends AppCompatActivity {
    Spinner spinnerProveedor;
    Cliente proveedorSeleccionado;
    CheckBox checkBoxCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_compra);

        ClienteService proveedorService = ClienteService.getInstance();
        List<Cliente> listaProveedor;
        ArrayAdapter<Cliente> adaptadorProveedor;
        proveedorSeleccionado = null;

        // Configuraciones para spinnerProveedor
        spinnerProveedor = findViewById(R.id.spinnerProveedor);
        listaProveedor = proveedorService.obtenerClientes();
        adaptadorProveedor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaProveedor);
        adaptadorProveedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProveedor.setAdapter(adaptadorProveedor);
        spinnerProveedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                proveedorSeleccionado = (Cliente) parentView.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejo cuando no se ha seleccionado nada
            }
        });

        checkBoxCliente = findViewById(R.id.checkBoxProveedor);

    }

    public void btnEventoFiltrarVenta(View v) {

        Intent principalIntent = new Intent(this, CompraActivity.class);
        Bundle bundle = new Bundle();

        if (!checkBoxCliente.isChecked()) {
            bundle.putString("consulta", "");
        } else {
            bundle.putString("consulta", "/" + proveedorSeleccionado.getId());
        }

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}