package com.example.mercadolibromobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.ItemCarrito;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<ItemCarrito> itemsCarrito;
    private Context context;

    public CarritoAdapter(List<ItemCarrito> itemsCarrito, Context context) {
        this.itemsCarrito = itemsCarrito;
        this.context = context;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        ItemCarrito item = itemsCarrito.get(position);

        holder.tvTituloLibro.setText(item.getTituloLibro());
        holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
        holder.tvPrecio.setText("Precio: $" + item.getPrecioUnitario());
    }

    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloLibro, tvCantidad, tvPrecio;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloLibro = itemView.findViewById(R.id.tvTituloLibro);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
