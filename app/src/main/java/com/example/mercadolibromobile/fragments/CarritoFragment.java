package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.adapters.CarritoAdapter;
import com.example.mercadolibromobile.models.ItemCarrito;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CarritoFragment extends Fragment {

    private RecyclerView recyclerViewCarrito;
    private TextView precioTotal;
    private List<ItemCarrito> itemsCarrito;
    private final String API_URL = "https://backend-mercado-libro-mobile.onrender.com/api/carrito/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerViewCarrito = view.findViewById(R.id.recyclerViewCarrito);
        precioTotal = view.findViewById(R.id.precioTotal);

        itemsCarrito = new ArrayList<>();

        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(getContext()));
        CarritoAdapter adapter = new CarritoAdapter(itemsCarrito, getContext());
        recyclerViewCarrito.setAdapter(adapter);

        obtenerDatosCarrito(adapter);

        return view;
    }

    private void obtenerDatosCarrito(CarritoAdapter adapter) {
        OkHttpClient client = new OkHttpClient();
        String token = getAccessToken();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override

            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Type listType = new TypeToken<List<ItemCarrito>>() {}.getType();
                    List<ItemCarrito> nuevosItems = new Gson().fromJson(responseData, listType);

                    requireActivity().runOnUiThread(() -> {
                        itemsCarrito.clear();
                        itemsCarrito.addAll(nuevosItems);
                        adapter.notifyDataSetChanged();
                        actualizarPrecioTotal();
                    });

                } else {
                    requireActivity().runOnUiThread(() -> {
                    });
                }
            }
        });
    }

    private String getAccessToken() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("access_token", null); // Devuelve el token de acceso
    }

    private void actualizarPrecioTotal() {
        double total = 0.0;
        for (ItemCarrito item : itemsCarrito) {
            total += item.getTotal();
        }
        precioTotal.setText("Total: $" + total);
    }
}

