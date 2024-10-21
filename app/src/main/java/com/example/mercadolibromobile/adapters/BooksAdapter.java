package com.example.mercadolibromobile.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log; // log para testear login
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.CarritoApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.fragments.SinopsisFragment;
import com.example.mercadolibromobile.models.Book;
import com.example.mercadolibromobile.models.ItemCarrito;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private final List<Book> books;
    private final FragmentActivity activity;
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";

    public BooksAdapter(List<Book> books, FragmentActivity activity) {
        this.books = books;
        this.activity = activity;
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

        Glide.with(holder.itemView.getContext())
                .load(book.getPortada())
                .timeout(10000)
                .into(holder.ivBookCover);

        // Botón para ver la sinopsis
        holder.btnSinopsis.setOnClickListener(v -> {
            SinopsisFragment fragment = SinopsisFragment.newInstance(
                    book.getTitulo(),
                    book.getDescripcion(),
                    book.getPortada()
            );
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Botón para comprar (Agregar al carrito)
        holder.btnComprar.setOnClickListener(v -> {
            // Ahora pasamos el id del libro, la cantidad (1) y el precio del libro
            ItemCarrito itemCarrito = new ItemCarrito(book.getId(), 1, book.getPrecio()); // Cantidad predeterminada 1
            agregarAlCarrito(itemCarrito);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookCover;
        TextView tvBookTitle, tvBookAuthor, tvBookPrice, tvBookStock, tvBookCategory;
        Button btnSinopsis, btnComprar;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice);
            tvBookStock = itemView.findViewById(R.id.tvBookStock);
            tvBookCategory = itemView.findViewById(R.id.tvBookCategory);
            btnSinopsis = itemView.findViewById(R.id.btnSinopsis);
            btnComprar = itemView.findViewById(R.id.btnComprar);
        }
    }

    private void agregarAlCarrito(ItemCarrito itemCarrito) {
        // Obtener el token de acceso desde las preferencias compartidas
        SharedPreferences prefs = activity.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);

        // Agregar log para ver el token
        Log.d("BooksAdapter", "Token de acceso obtenido: " + token);

        if (token == null) {
            Toast.makeText(activity, "Token no encontrado", Toast.LENGTH_SHORT).show();
            return; // Salir si no hay token
        }

        // Log para verificar el contenido del itemCarrito
        Log.d("BooksAdapter", "Agregando al carrito: ID libro: " + itemCarrito.getId_libro() +
                ", Cantidad: " + itemCarrito.getCantidad() +
                ", Precio: " + itemCarrito.getPrecio());

        // Llamar a la API del carrito
        CarritoApi carritoApi = RetrofitClient.getInstance(BASE_URL).create(CarritoApi.class);
        Call<ItemCarrito> call = carritoApi.agregarAlCarrito("Bearer " + token, itemCarrito);

        call.enqueue(new Callback<ItemCarrito>() {
            @Override
            public void onResponse(Call<ItemCarrito> call, Response<ItemCarrito> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Libro agregado al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    // Agregar log para ver el código de respuesta y el cuerpo de error
                    Log.e("BooksAdapter", "Error al agregar al carrito. Código de respuesta: " + response.code() +
                            ", Cuerpo de error: " + response.errorBody());
                    Toast.makeText(activity, "Error al agregar al carrito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCarrito> call, Throwable t) {
                Toast.makeText(activity, "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
