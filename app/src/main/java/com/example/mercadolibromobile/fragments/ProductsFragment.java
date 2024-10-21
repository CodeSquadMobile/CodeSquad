package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.adapters.BooksAdapter;
import com.example.mercadolibromobile.api.BookApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//       String baseUrl = "http://192.168.0.50:8000/api/"; // Leo
//        String baseUrl ="http://10.0.2.2:8000/api/";    // Marce
//        String baseUrl ="http://192.168.100.26:8000/api/"; // Nahir
//        String baseUrl ="http://192.168.0.244:8000/api/"; // Ivette
//        String baseUrl = "http://192.168.0.53:8000/api/";  // Invitado

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerViewBooks;
    private BooksAdapter booksAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        // Inicializar el RecyclerView
        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Llamar a la API y obtener los libros
        fetchBooks();

        return view;
    }

    private void fetchBooks() {
        // Inicializa Retrofit
        String baseUrl = "https://backend-mercado-libro-mobile.onrender.com/api/";
        BookApi bookApi = RetrofitClient.getInstance(baseUrl).create(BookApi.class);

        Call<List<Book>> call = bookApi.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    // Configurar el adaptador con los libros
                    booksAdapter = new BooksAdapter(books);
                    recyclerViewBooks.setAdapter(booksAdapter);
                } else {
                    // Manejo del error cuando la respuesta no es exitosa
                    Log.e("API Error", "Código de respuesta: " + response.code() + ", Mensaje: " + response.message());
                    Toast.makeText(getContext(), getString(R.string.error_respuesta, response.message()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), getString(R.string.error_cargar_libros, t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}