package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.ApiService;
import com.example.mercadolibromobile.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView emailTextView;
    private Button deleteAccountButton;
    private ApiService userService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "No email found");
        emailTextView.setText(userEmail);

        // Configurar el botón "Dar de Baja Usuario"
        deleteAccountButton = rootView.findViewById(R.id.button9);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accessToken = getAccessToken();
                deleteUser(accessToken);
            }
        });

        // Inicializar el servicio de usuario con Retrofit
        initUserService();

        return rootView;
    }

    private String getAccessToken() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("access_token", null);
    }

    private String getUserId() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("user_id", null); // Recupera el ID del usuario
    }

    private void initUserService() {
        userService = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/")
                .create(ApiService.class);
    }

    private void deleteUser(String accessToken) {
        String userId = getUserId();
        if (userId == null) {
            Toast.makeText(requireContext(), "ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }
        userService.deleteUser("Bearer " + accessToken, userId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireContext(), "Cuenta dada de baja correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Error al dar de baja la cuenta", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(requireContext(), "Error al dar de baja la cuenta: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
