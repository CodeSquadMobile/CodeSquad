package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.User;
import com.example.mercadolibromobile.api.ApiClient;
import com.example.mercadolibromobile.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        emailTextView = rootView.findViewById(R.id.textView9);  // Referencia al TextView del email

        fetchUserData();

        return rootView;
    }

    private void fetchUserData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<User>> call = apiService.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body();
                    if (!userList.isEmpty()) {
                        // Obtener el email del primer usuario y mostrarlo en el TextView
                        String email = userList.get(0).getEmail();
                        emailTextView.setText(email);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Manejo de error, puedes mostrar un mensaje en caso de fallo
                emailTextView.setText("Error al obtener datos");
            }
        });
    }
}
