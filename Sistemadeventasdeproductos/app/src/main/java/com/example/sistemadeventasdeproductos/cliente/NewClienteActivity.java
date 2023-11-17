package com.example.sistemadeventasdeproductos.cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;

public class NewClienteActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText ruc;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cliente);
        nombre = findViewById(R.id.txtNombreNuevo);
        apellido = findViewById(R.id.txtApellidoNuevo);
        ruc = findViewById(R.id.txtRucNuevo);
        email = findViewById(R.id.txtEmailNuevo);
    }

    public void agregarNuevoCliente(View v) {

        Cliente cliente = new Cliente();
        ClienteService clienteService = ClienteService.getInstance();

        cliente.setNombre(nombre.getText().toString());
        cliente.setApellido(apellido.getText().toString());
        cliente.setRuc(ruc.getText().toString());
        cliente.setEmail(email.getText().toString());

        clienteService.agregarCliente(cliente);

        Toast.makeText(
                NewClienteActivity.this,R.string.clienteAgregadoCorrectamente,Toast.LENGTH_LONG).show();

        Intent newClienteIntent = new Intent(this, ClienteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newClienteIntent.putExtras(bundle);
        startActivity(newClienteIntent);
        finish();
    }

}