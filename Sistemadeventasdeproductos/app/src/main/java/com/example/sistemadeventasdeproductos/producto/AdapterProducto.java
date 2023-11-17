package com.example.sistemadeventasdeproductos.producto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import java.util.List;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> {
    private List<Producto> dsProducto;
    private ItemClickListener mItemListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_producto, viewGroup,false);
        ViewHolder pvh=new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvId.setText(dsProducto.get(i).getId().toString());
        viewHolder.tvCodigo.setText(dsProducto.get(i).getCodigo());
        viewHolder.tvNombre.setText(dsProducto.get(i).getNombre());
        viewHolder.tvCategoria.setText(dsProducto.get(i).getCategoria().toString());
        viewHolder.tvPrecio.setText(dsProducto.get(i).getPrecioVenta().toString());

        viewHolder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(dsProducto.get(i));
        });

    }

    @Override
    public int getItemCount() {
        return dsProducto.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvCodigo;
        public TextView tvNombre;
        public TextView tvCategoria;
        public TextView tvPrecio;
        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvCodigo = v.findViewById(R.id.txtCodigo);
            tvNombre = v.findViewById(R.id.txtNombre);
            tvCategoria = v.findViewById(R.id.txtCategoria);
            tvPrecio = v.findViewById(R.id.txtPrecio);
        }
    }

    public AdapterProducto(List<Producto> listaProductos) {
        this.dsProducto = listaProductos;
    }

    public interface ItemClickListener{
        void onItemClick(Producto producto);
    }

}