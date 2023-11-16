package com.example.sistemadeventasdeproductos.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.categoria.CategoriasActivity;

public class FiltroClienteActivity extends AppCompatActivity {
    String value;
    EditText nombre;
    EditText apellido;
    EditText ruc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_cliente);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
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