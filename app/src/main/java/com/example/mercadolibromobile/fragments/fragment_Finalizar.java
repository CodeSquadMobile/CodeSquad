package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.mercadolibromobile.adapters.CarritoAdapter;
import com.example.mercadolibromobile.api.BookApi;
import com.example.mercadolibromobile.api.CarritoApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.ItemCarrito;
import com.example.mercadolibromobile.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_Finalizar extends Fragment {

    private RecyclerView recyclerViewCarrito;
    private CarritoAdapter carritoAdapter;
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";
    private static final String TAG = "Fragment_Finalizar";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__finalizar, container, false);

        recyclerViewCarrito = view.findViewById(R.id.recyclerViewCarrito);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener el carrito
        obtenerCarrito();

        return view;
    }

    private void obtenerCarrito() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);

        Log.d(TAG, "Token recibido: " + token);

        if (token == null) {
            Toast.makeText(getContext(), "Token no encontrado. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Token no encontrado.");
            return;
        }

        CarritoApi carritoApi = RetrofitClient.getInstance(BASE_URL).create(CarritoApi.class);
        Call<List<ItemCarrito>> call = carritoApi.obtenerCarrito("Bearer " + token);

        Log.d(TAG, "Llamando a la API del carrito con el token: " + token);

        call.enqueue(new Callback<List<ItemCarrito>>() {
            @Override
            public void onResponse(Call<List<ItemCarrito>> call, Response<List<ItemCarrito>> response) {
                if (response.isSuccessful()) {
                    List<ItemCarrito> items = response.body();
                    if (items != null) {
                        // Aquí obtenemos los libros del carrito
                        obtenerListaLibros(items);
                    } else {
                        Log.e(TAG, "Respuesta del carrito es null.");
                        Toast.makeText(getContext(), "Error al cargar el carrito: respuesta vacía.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + response.message());
                    if (response.code() == 403 || response.code() == 401) {
                        Toast.makeText(getContext(), "Acceso denegado. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error " + response.code() + ": Acceso denegado.");
                    } else {
                        Toast.makeText(getContext(), "Error al cargar el carrito", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ItemCarrito>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error de conexión: " + t.getMessage());
            }
        });
    }

    // Método para obtener la lista de libros
    private void obtenerListaLibros(List<ItemCarrito> itemsCarrito) {
        BookApi bookApi = RetrofitClient.getInstance(BASE_URL).create(BookApi.class);
        Call<List<Book>> callLibros = bookApi.getBooks(); // Obtener todos los libros

        callLibros.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> libros = response.body();
                    if (libros != null) {
                        // Iniciar el adaptador con los datos del carrito y libros
                        carritoAdapter = new CarritoAdapter(itemsCarrito, libros);
                        recyclerViewCarrito.setAdapter(carritoAdapter);
                        Log.d(TAG, "Carrito cargado exitosamente con " + itemsCarrito.size() + " items.");
                    } else {
                        Log.e(TAG, "Respuesta de libros es null.");
                        Toast.makeText(getContext(), "Error al cargar los libros: respuesta vacía.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta de libros: " + response.code() + " - " + response.message());
                    Toast.makeText(getContext(), "Error al cargar los libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión al cargar libros", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error de conexión: " + t.getMessage());
            }
        });
    }
}
