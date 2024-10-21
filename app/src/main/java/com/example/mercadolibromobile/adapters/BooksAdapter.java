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
        holder.tvBookPrice.setText("Precio: $" + book.getPrecio());
        holder.tvBookStock.setText("En stock: " + book.getStock());
        Glide.with(holder.itemView.getContext())
                .load(book.getPortada())
                .timeout(10000) // 10 segundos
                .into(holder.ivBookCover);

        holder.btnSinopsis.setOnClickListener(v -> {
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
        TextView tvBookTitle, tvBookPrice, tvBookStock;
        Button btnSinopsis;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice);
            tvBookStock = itemView.findViewById(R.id.tvBookStock);
            btnSinopsis = itemView.findViewById(R.id.btnSinopsis);
        }
    }
}
