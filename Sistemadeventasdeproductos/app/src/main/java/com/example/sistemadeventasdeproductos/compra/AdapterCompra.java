package com.example.sistemadeventasdeproductos.compra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Compra;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.venta.AdapterVenta;

import java.util.List;

public class AdapterCompra {

    private List<Compra> dsCompra;
    private AdapterCompra.ItemClickListener mItemListener;

    public List<Venta> getListaCompras() {
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
    public void onBindViewHolder(@NonNull AdapterVenta.ViewHolder viewHolder, int i) {

        viewHolder.tvId.setText(dsVenta.get(i).getId().toString());
        viewHolder.tvCliente.setText(dsVenta.get(i).getCliente().toString());
        viewHolder.tvNroFactura.setText(dsVenta.get(i).getNroFactura().toString());
        viewHolder.tvFecha.setText(dsVenta.get(i).getFecha().toString());
        viewHolder.tvTotal.setText(dsVenta.get(i).getTotal().toString());

        // Se establece la accion que se realizara al hacer clic en un item
        viewHolder.itemView.setOnClickListener(view -> {
            // Verifica si hay un ItemClickListener y si sí, realiza la acción
            if (mItemListener != null) {
                mItemListener.onItemClick(dsVenta.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsVenta.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvCliente;
        public TextView tvNroFactura;
        public TextView tvFecha;
        public TextView tvTotal;
        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvCliente = v.findViewById(R.id.txtCliente);
            tvNroFactura = v.findViewById(R.id.txtNroFactura);
            tvFecha = v.findViewById(R.id.txtFecha);
            tvTotal = v.findViewById(R.id.txtTotal);
        }
    }

    public AdapterVenta(List<Venta> listaVentas) {
        this.dsVenta = listaVentas;
    }

    public interface ItemClickListener{
        void onItemClick(Venta venta);
    }

    public void setItemClickListener(AdapterVenta.ItemClickListener listener) {
        this.mItemListener = listener;
    }
}
