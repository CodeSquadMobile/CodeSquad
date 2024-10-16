package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.example.mercadolibromobile.models.Categoria;
import com.example.mercadolibromobile.api.CategoriaApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerViewBooks;
    private BooksAdapter booksAdapter;
    private Spinner categorySelector;
    private List<String> categoriasList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        // Inicializar el RecyclerView
        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el Spinner de categorías
        categorySelector = view.findViewById(R.id.category_selector);

        // Llamar a la API y obtener las categorías
        fetchCategorias();

        // Llamar a la API y obtener los libros
        fetchBooks();

        return view;
    }

    private void fetchCategorias() {
        // Inicializa Retrofit para la API de categorías
        String baseUrl = "http://192.168.0.244:8000/api/";
        CategoriaApi categoriaApi = RetrofitClient.getInstance(baseUrl).create(CategoriaApi.class);

        Call<List<Categoria>> call = categoriaApi.getCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categoria> categorias = response.body();
                    for (Categoria categoria : categorias) {
                        categoriasList.add(categoria.getNombreCategoria());
                    }
                    // Configurar el adaptador del Spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriasList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySelector.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
                Toast.makeText(getContext(), "Error al cargar las categorías: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
    }

    private void fetchBooks() {
        String selectedCategory = categorySelector.getSelectedItem() != null ? categorySelector.getSelectedItem().toString() : "";

        // Inicializa Retrofit
        String baseUrl = "http://192.168.0.244:8000/api/";
        BookApi bookApi = RetrofitClient.getInstance(baseUrl).create(BookApi.class);

        Call<List<Book>> call = bookApi.getBooks("", selectedCategory);
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
                    Toast.makeText(getContext(), "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error al cargar los libros: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

