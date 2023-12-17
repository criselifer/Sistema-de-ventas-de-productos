package com.example.sistemadeventasdeproductos.producto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import com.example.sistemadeventasdeproductos.api.models.Producto;
import com.example.sistemadeventasdeproductos.categoria.AdapterCategoria;

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
        viewHolder.tvCodigo.setText("Código: "+dsProducto.get(i).getCodigo());
        viewHolder.tvNombre.setText("Producto: "+dsProducto.get(i).getNombre());
        viewHolder.tvCategoria.setText("Categoría: "+dsProducto.get(i).getCategoria().toString());
        viewHolder.tvPrecio.setText("Precio de Venta: "+ dsProducto.get(i).getPrecioVenta().toString());
        viewHolder.tvExistencia.setText("Existencia: "+dsProducto.get(i).getExistencia().toString());

        // Se establece la accion que se realizara al hacer clic en un item
        viewHolder.itemView.setOnClickListener(view -> {
            // Verifica si hay un ItemClickListener y si sí, realiza la acción
            if (mItemListener != null) {
                mItemListener.onItemClick(dsProducto.get(i));
            }
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
        public TextView tvExistencia;

        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvCodigo = v.findViewById(R.id.txtCodigo);
            tvNombre = v.findViewById(R.id.txtNombre);
            tvCategoria = v.findViewById(R.id.txtCategoria);
            tvPrecio = v.findViewById(R.id.txtPrecio);
            tvExistencia = v.findViewById(R.id.txtExistencia);
        }
    }

    public AdapterProducto(List<Producto> listaProductos) {
        this.dsProducto = listaProductos;
    }

    public interface ItemClickListener{
        void onItemClick(Producto producto);
    }

    public void setItemClickListener(AdapterProducto.ItemClickListener listener) {
        this.mItemListener = listener;
    }

}