package com.example.sistemadeventasdeproductos.cliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.services.ClienteService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ClienteActivity extends AppCompatActivity {

    private RecyclerView rvCliente;
    private AdapterCliente adapterCliente;
    private FloatingActionButton fabNuevoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        rvCliente = findViewById(R.id.rvListadoClientes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCliente.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarClientes(filtro);

        fabNuevoCliente=findViewById(R.id.fabNuevoCliente);
        fabNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newClienteIntent = new Intent(ClienteActivity.this, NewClienteActivity.class);
                startActivity(newClienteIntent);
                finish();
            }
        });

    }

    public void cargarClientes(String filtro) {

        ClienteService clienteService = ClienteService.getInstance();
        List<Cliente> listaClientes = new ArrayList<>();

        if (filtro.equals("")) {
            listaClientes = clienteService.obtenerClientes();
        } else {
            String[] filtroSplit = filtro.split("/");
            if (filtroSplit.length > 1) {
                String tipoFiltro = filtroSplit[0];
                String valorFiltro = filtroSplit[1];
                switch (tipoFiltro) {
                    case "nombre":
                        listaClientes = clienteService.clientesByNombre(valorFiltro);
                        break;
                    case "apellido":
                        listaClientes = clienteService.clientesByApellido(valorFiltro);
                        break;
                    case "nombre-apellido":
                        listaClientes = clienteService.clientesByNombreYApellido(filtroSplit[1], filtroSplit[2]);
                        break;
                }
            }
        }

        adapterCliente = new AdapterCliente(listaClientes);
        rvCliente.setAdapter(adapterCliente);

    }

    public void eliminarCliente(View view) {

        ClienteService clienteService = ClienteService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idCliente = Integer.parseInt( id.getText().toString() );

        clienteService.eliminarClienteById( idCliente );

        Intent newClienteIntent = new Intent(this, ClienteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newClienteIntent.putExtras(bundle);
        startActivity(newClienteIntent);
        finish();

    }

}