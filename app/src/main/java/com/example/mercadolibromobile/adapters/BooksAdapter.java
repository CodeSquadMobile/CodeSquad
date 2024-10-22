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
import com.example.mercadolibromobile.models.ItemCarrito; // Asegúrate de que esta clase siga existiendo.

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
        // Logs para verificar los datos del libro
        Log.d("BooksAdapter", "ID del libro: " + book.getIdLibro());
        Log.d("BooksAdapter", "Título del libro: " + book.getTitulo());
        Log.d("BooksAdapter", "Precio del libro: " + book.getPrecio());
        Log.d("BooksAdapter", "Stock del libro: " + book.getStock());

        holder.tvBookTitle.setText(book.getTitulo());
        holder.tvBookPrice.setText("Precio: $" + book.getPrecio());
        holder.tvBookStock.setText("En stock: " + book.getStock());

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
            agregarAlCarrito(book); // Pasar el libro directamente
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookCover;
        TextView tvBookTitle, tvBookPrice, tvBookStock;
        Button btnSinopsis, btnComprar;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice);
            tvBookStock = itemView.findViewById(R.id.tvBookStock);
            btnSinopsis = itemView.findViewById(R.id.btnSinopsis);
            btnComprar = itemView.findViewById(R.id.btnComprar);
        }
    }

    private void agregarAlCarrito(Book book) {
        // Obtener el token de acceso
        String token = getAccessToken();

        // Agregar log para verificar el token
        Log.d("BooksAdapter", "Token de acceso obtenido adaptador book: " + token);

        if (token == null) {
            Toast.makeText(activity, "Token no encontrado. Por favor, inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            return; // Salir si no hay token
        }

        // Crear un objeto ItemCarrito solo con los datos necesarios (ID, cantidad y precio)
        ItemCarrito itemCarrito = new ItemCarrito(book.getIdLibro(), 1, book.getPrecio());

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
                    Log.e("BooksAdapter", "Error al agregar al carrito. Código de respuesta: " + response.code() +
                            ", Cuerpo de error: " + response.errorBody());
                    Toast.makeText(activity, "Error al agregar al carrito. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCarrito> call, Throwable t) {
                Log.e("BooksAdapter", "Fallo la conexión: " + t.getMessage());
                Toast.makeText(activity, "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para obtener el token de acceso
    private String getAccessToken() {
        SharedPreferences prefs = activity.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("access_token", null);
    }
}
