package com.example.sistemadeventasdeproductos.categoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Categoria;
import java.util.List;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolder> {
    private List<Categoria> dsCategoria;
    private ItemClickListener mItemListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_categoria, viewGroup,false);
        ViewHolder pvh=new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvId.setText(dsCategoria.get(i).getId().toString());
        viewHolder.tvNombre.setText(dsCategoria.get(i).getNombre());

        // Se establece la accion que se realizara al hacer clic en un item
        viewHolder.itemView.setOnClickListener(view -> {
            // Verifica si hay un ItemClickListener y si sí, realiza la acción
            if (mItemListener != null) {
                mItemListener.onItemClick(dsCategoria.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsCategoria.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvNombre;
        public ViewHolder (View v) {
            super(v);
            tvId = v.findViewById(R.id.txtId);
            tvNombre = v.findViewById(R.id.txtNombre);
        }
    }

    public AdapterCategoria(List<Categoria> listaCategorias) {
        this.dsCategoria = listaCategorias;
    }

    public interface ItemClickListener{
        void onItemClick(Categoria categoria);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mItemListener = listener;
    }

}