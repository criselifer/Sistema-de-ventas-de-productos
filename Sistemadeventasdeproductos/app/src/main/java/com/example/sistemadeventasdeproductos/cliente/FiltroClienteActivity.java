package com.example.sistemadeventasdeproductos.cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import java.util.ArrayList;

public class FiltroClienteActivity extends AppCompatActivity {
    AutoCompleteTextView nombre;
    AutoCompleteTextView apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_cliente);

        ClienteService clienteService = ClienteService.getInstance();
        ArrayList<String> nombres = clienteService.getNombres();
        nombre = findViewById(R.id.autoCompleteTextViewNombre);
        ArrayAdapter<String> adapterNombres = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres);
        nombre.setAdapter(adapterNombres);

        ArrayList<String> apellidos = clienteService.getApellidos();
        apellido = findViewById(R.id.autoCompleteTextViewApellido);
        ArrayAdapter<String> adapterApellidos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, apellidos);
        apellido.setAdapter(adapterApellidos);

    }

    public void btnEventoFiltrarCliente(View v) {

        Intent principalIntent = new Intent(this, ClienteActivity.class);
        Bundle bundle = new Bundle();

        if(nombre.getText().toString().equalsIgnoreCase("") && apellido.getText().toString().equalsIgnoreCase("")){
            bundle.putString("consulta","");
        } else if (!nombre.getText().toString().equalsIgnoreCase("") && apellido.getText().toString().equalsIgnoreCase("")) {
            bundle.putString("consulta","nombre/"+nombre.getText().toString());
        } else if (nombre.getText().toString().equalsIgnoreCase("") && !apellido.getText().toString().equalsIgnoreCase("")) {
            bundle.putString("consulta","apellido/"+apellido.getText().toString());
        } else {
            bundle.putString("consulta","nombre-apellido/"+nombre.getText().toString()+"/"+apellido.getText().toString());
        }

        principalIntent.putExtras(bundle);
        startActivity(principalIntent);

    }
}