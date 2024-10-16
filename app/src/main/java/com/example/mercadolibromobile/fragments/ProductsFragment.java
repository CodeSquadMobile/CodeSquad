package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.adapters.BooksAdapter;
import com.example.mercadolibromobile.models.Book;
import com.example.mercadolibromobile.api.BookApi;
import com.example.mercadolibromobile.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerViewBooks;
    private BooksAdapter booksAdapter;
    private EditText searchBar;
    private Spinner categorySelector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        // Inicializar el RecyclerView
        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el campo de búsqueda y el spinner de categorías
        searchBar = view.findViewById(R.id.search_bar);
        categorySelector = view.findViewById(R.id.category_selector);

        // Escuchar cambios en el buscador (cuando el usuario presiona "Enter")
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            fetchBooks();
            return true;
        });

        // Escuchar cambios en la selección de categoría
        categorySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchBooks();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Llamar a la API y obtener los libros
        fetchBooks();

        return view;
    }

    private void fetchBooks() {
        // Obtener el texto de búsqueda y la categoría seleccionada
        String searchQuery = searchBar.getText().toString();
        String selectedCategory = categorySelector.getSelectedItem().toString();

        // Inicializa Retrofit
        String baseUrl = "http://192.168.0.244:8000/api/";
        BookApi bookApi = RetrofitClient.getInstance(baseUrl).create(BookApi.class);

        // Llamada a la API con los filtros
        Call<List<Book>> call = bookApi.getBooks(searchQuery, selectedCategory);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    // Configurar el adaptador con los libros
                    booksAdapter = new BooksAdapter(books);
                    recyclerViewBooks.setAdapter(booksAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
                Toast.makeText(getContext(), "Error al cargar los libros: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
