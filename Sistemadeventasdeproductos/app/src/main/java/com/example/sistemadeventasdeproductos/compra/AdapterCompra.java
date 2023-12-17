package com.example.sistemadeventasdeproductos.compra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Compra;
import com.example.sistemadeventasdeproductos.venta.AdapterVentas;

import java.util.List;

public class AdapterCompra extends RecyclerView.Adapter<AdapterCompra.ViewHolder> {

    private List<Compra> dsCompra;
    private AdapterCompra.ItemClickListener mItemListener;

    public List<Compra> getListaCompras() {
        return dsCompra;
    }

    @NonNull
    @Override
    public AdapterCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_compra, viewGroup,false);
        AdapterCompra.ViewHolder pvh=new AdapterCompra.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvId.setText(dsCompra.get(i).getId().toString());
        viewHolder.tvProveedor.setText(dsCompra.get(i).getProveedor().toString());
        viewHolder.tvNroFactura.setText(dsCompra.get(i).getNroFactura().toString());
        viewHolder.tvFecha.setText(dsCompra.get(i).getFecha().toString());
        viewHolder.tvTotal.setText(dsCompra.get(i).getTotal().toString());

        // Se establece la accion que se realizara al hacer clic en un item
        viewHolder.itemView.setOnClickListener(view -> {
            // Verifica si hay un ItemClickListener y si sí, realiza la acción
            if (mItemListener != null) {
                mItemListener.onItemClick(dsCompra.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsCompra.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvProveedor;
        public TextView tvNroFactura;
        public TextView tvFecha;
        public TextView tvTotal;
        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvProveedor = v.findViewById(R.id.txtCliente);
            tvNroFactura = v.findViewById(R.id.txtNroFactura);
            tvFecha = v.findViewById(R.id.txtFecha);
            tvTotal = v.findViewById(R.id.txtTotal);
        }
    }

    public AdapterCompra(List<Compra> listaCompras) {
        this.dsCompra = listaCompras;
    }

    public interface ItemClickListener{
        void onItemClick(Compra compra);
    }

    public void setItemClickListener(com.example.sistemadeventasdeproductos.compra.AdapterCompra.ItemClickListener listener) {
        this.mItemListener = listener;
    }
}
