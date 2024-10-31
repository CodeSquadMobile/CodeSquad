package com.example.mercadolibromobile.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.Book;
import com.example.mercadolibromobile.models.ItemCarrito;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private final List<ItemCarrito> itemsCarrito;
    private final List<Book> libros;
    private static final String TAG = "CarritoAdapter";

    public CarritoAdapter(List<ItemCarrito> itemsCarrito, List<Book> libros) {
        this.itemsCarrito = itemsCarrito;
        this.libros = libros;
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
        Book libro = findBookById(item.getLibro());

        if (libros != null && !libros.isEmpty()) {
            if (libro != null) {
                holder.tvTituloLibro.setText(libro.getTitulo());
                holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
                holder.tvPrecio.setText("Precio: $" + (item.getCantidad() * libro.getPrecio()));
                Log.d(TAG, "Libro encontrado: " + libro.getTitulo());
            } else {
                holder.tvTituloLibro.setText("Libro no encontrado");
                holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
                holder.tvPrecio.setText("Precio: $0");
                Log.e(TAG, "Libro no encontrado para el ID: " + item.getLibro());
            }
        } else {
            Log.e(TAG, "La lista de libros está vacía o es nula");
            holder.tvTituloLibro.setText("Libro no disponible");
            holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
            holder.tvPrecio.setText("Precio: $0");
        }
    }

    private Book findBookById(int id) {
        for (Book libro : libros) {
            if (libro.getIdLibro() == id) {
                return libro;
            }
        }
        return null;
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
