package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log; // Importa Log
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
import com.example.mercadolibromobile.api.CarritoApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.ItemCarrito;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_Finalizar extends Fragment {

    private RecyclerView recyclerViewCarrito;
    private CarritoAdapter carritoAdapter;
    private static final String BASE_URL = "https://192.168.100.26:8000/api/";
    private static final String TAG = "Fragment_Finalizar"; // Etiqueta para los logs

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

        // Log para verificar el token
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
                    carritoAdapter = new CarritoAdapter(items);
                    recyclerViewCarrito.setAdapter(carritoAdapter);
                    Log.d(TAG, "Carrito cargado exitosamente con " + items.size() + " items.");
                } else {
                    if (response.code() == 403 || response.code() == 401) {
                        clearSharedPreferences();
                        Toast.makeText(getContext(), "Acceso denegado. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error " + response.code() + ": Acceso denegado.");
                    } else {
                        Toast.makeText(getContext(), "Error al cargar el carrito", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + response.message());
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

    private void clearSharedPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Log.d(TAG, "SharedPreferences limpiados.");
    }
}
