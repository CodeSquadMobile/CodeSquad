package com.example.mercadolibromobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.ItemCarrito;
import com.example.mercadolibromobile.models.Book;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private final List<ItemCarrito> itemsCarrito;
    private final List<Book> libros;

    public CarritoAdapter(List<ItemCarrito> itemsCarrito, List<Book> libros) {
        this.itemsCarrito = itemsCarrito;
        this.libros = libros;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCarrito item = itemsCarrito.get(position);

        // Obtén el libro correspondiente usando el ID del libro
        Book libro = findBookById(item.getId_libro());

        if (libro != null) {
            holder.tvTituloLibro.setText(libro.getTitulo()); // Asigna el título del libro
            holder.tvCantidad.setText(String.valueOf(item.getCantidad())); // Asigna la cantidad
            holder.tvPrecio.setText(String.format("%.2f", libro.getPrecio() * item.getCantidad())); // Asigna el precio total
        }
    }

    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }

    // Método para encontrar un libro por su ID
    private Book findBookById(int idLibro) {
        for (Book libro : libros) {
            if (libro.getIdLibro() == idLibro) { // Usando getIdLibro() para acceder al ID
                return libro;
            }
        }
        return null; // Retorna null si no se encuentra el libro
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloLibro;
        TextView tvCantidad;
        TextView tvPrecio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloLibro = itemView.findViewById(R.id.tvTituloLibro); // Actualizado
            tvCantidad = itemView.findViewById(R.id.tvCantidad); // Actualizado
            tvPrecio = itemView.findViewById(R.id.tvPrecio); // Actualizado
        }
    }
}
