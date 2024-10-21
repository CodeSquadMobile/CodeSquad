package com.example.mercadolibromobile.adapters;

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

    private final List<ItemCarrito> itemsCarrito;

    public CarritoAdapter(List<ItemCarrito> itemsCarrito) {
        this.itemsCarrito = itemsCarrito;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        ItemCarrito item = itemsCarrito.get(position);

        // Aquí mostramos los detalles del libro en el carrito, como título, cantidad y precio
        holder.tvTituloLibro.setText("Libro: " + item.getId_libro());  // Debes obtener el título del libro por el ID
        holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
        holder.tvPrecio.setText("Precio: $" + item.getCantidad() * item.getPrecio());  // Suponiendo que tienes un método getPrecio()
    }

    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }

    static class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloLibro, tvCantidad, tvPrecio;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloLibro = itemView.findViewById(R.id.tvTituloLibro);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
