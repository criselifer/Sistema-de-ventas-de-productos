package com.example.sistemadeventasdeproductos.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder> {
    private List<Cliente> dsCliente;
    private ItemClickListener mItemListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cliente, viewGroup,false);
        ViewHolder pvh=new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvId.setText(dsCliente.get(i).getId().toString());
        viewHolder.tvCliente.setText(dsCliente.get(i).toString());
        viewHolder.tvRuc.setText(dsCliente.get(i).getRuc());
        viewHolder.tvEmail.setText(dsCliente.get(i).getEmail());

        viewHolder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(dsCliente.get(i));
        });

    }

    @Override
    public int getItemCount() {
        return dsCliente.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvCliente;
        public TextView tvRuc;
        public TextView tvEmail;
        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvCliente = v.findViewById(R.id.txtCliente);
            tvRuc = v.findViewById(R.id.txtRuc);
            tvEmail = v.findViewById(R.id.txtEmail);
        }
    }

    public AdapterCliente(List<Cliente> listaClientes) {
        this.dsCliente = listaClientes;
    }

    public interface ItemClickListener{
        void onItemClick(Cliente cliente);
    }

}