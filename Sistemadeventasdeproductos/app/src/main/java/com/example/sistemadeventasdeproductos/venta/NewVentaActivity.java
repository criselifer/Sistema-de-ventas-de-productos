package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import java.util.Date;
import java.util.List;

public class NewVentaActivity extends AppCompatActivity {

    private Cliente clienteSeleccionado;
    private Spinner spinnerCliente;
    private EditText nroFactura;
    //private EditText fecha;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_venta);

        ClienteService clienteService = ClienteService.getInstance();
        List<Cliente> listaClientes;
        ArrayAdapter<Cliente> adaptadorCliente;

        // Configuraciones para spinnerCliente
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

        nroFactura = findViewById(R.id.txtNroFactura);
        //fecha = findViewById(R.id.txtFecha);
        total = findViewById(R.id.txtTotal);

    }

    public void agregarNuevaVenta(View v) {

        Venta venta = new Venta();
        VentaService ventaService = VentaService.getInstance();

        venta.setCliente(clienteSeleccionado);
        venta.setNroFactura(Integer.parseInt(nroFactura.getText().toString()));
        venta.setFecha(new Date());
        try {
            venta.setTotal(Integer.parseInt(total.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            venta.setTotal(0);
        }
        ventaService.agregarVenta(venta);

        Toast.makeText(
                NewVentaActivity.this,R.string.ventaAgregadoCorrectamente,Toast.LENGTH_LONG).show();

        Intent newVentaIntent = new Intent(this, VentaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("consulta","");
        newVentaIntent.putExtras(bundle);
        startActivity(newVentaIntent);
        finish();
    }

}