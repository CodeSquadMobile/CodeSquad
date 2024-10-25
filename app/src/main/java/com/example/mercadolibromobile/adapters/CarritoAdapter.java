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

        // Obtener el libro correspondiente usando el ID
        Book libro = findBookById(item.getId_libro());

        if (libro != null) {
            holder.tvTituloLibro.setText(libro.getTitulo()); // Mostrar el título del libro
            holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
            holder.tvPrecio.setText("Precio: $" + (item.getCantidad() * libro.getPrecio())); // Calcular precio total
            Log.d(TAG, "Libro encontrado: " + libro.getTitulo());
        } else {
            holder.tvTituloLibro.setText("Libro no encontrado");
            holder.tvCantidad.setText("Cantidad: " + item.getCantidad());
            holder.tvPrecio.setText("Precio: $0"); // Mostrar precio 0 si no se encuentra el libro
            Log.e(TAG, "Libro no encontrado para el ID: " + item.getId_libro());
        }
    }

    private Book findBookById(int id) {
        for (Book libro : libros) {
            if (libro.getIdLibro() == id) {
                return libro;
            }
        }
        return null; // Si no se encuentra el libro
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
