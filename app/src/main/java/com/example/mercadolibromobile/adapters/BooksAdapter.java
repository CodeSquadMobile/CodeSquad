package com.example.mercadolibromobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mercadolibromobile.BookSynopsisDialogFragment; // Ajusta el paquete según sea necesario


import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.Book;
import com.bumptech.glide.Glide;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private final List<Book> books;

    public BooksAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvBookTitle.setText(book.getTitulo());
        holder.tvBookAuthor.setText(book.getAutor());
        holder.tvBookPrice.setText("Precio: $" + book.getPrecio());
        holder.tvBookStock.setText("En stock: " + book.getStock());
        holder.tvBookCategory.setText("Categoría: " + book.getCategoria());

        // Cargar la portada del libro con Glide
        Glide.with(holder.itemView.getContext())
                .load(book.getPortada())
                .timeout(10000) // 10 segundos
                .into(holder.ivBookCover);

        // Manejar el clic en el botón de sinopsis
        holder.btnSinopsis.setOnClickListener(v -> {
            // Crear una instancia del diálogo y pasar la descripción del libro
            BookSynopsisDialogFragment dialog = BookSynopsisDialogFragment.newInstance(book.getDescripcion());
            dialog.show(((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager(), "BookSynopsisDialogFragment");
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookCover;
        TextView tvBookTitle, tvBookAuthor, tvBookPrice, tvBookStock, tvBookCategory; // Campos de texto para los detalles del libro
        Button btnSinopsis; // Botón para mostrar la sinopsis

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice);
            tvBookStock = itemView.findViewById(R.id.tvBookStock);
            tvBookCategory = itemView.findViewById(R.id.tvBookCategory);
            btnSinopsis = itemView.findViewById(R.id.btnSinopsis); // Asignar el botón para la sinopsis
        }
    }
}
