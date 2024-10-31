package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.LoginActivity;
import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.deleteApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private TextView emailTextView;
    private Button deleteAccountButton;
    private deleteApi apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        deleteAccountButton = rootView.findViewById(R.id.button9);  // Cambia 'view' por 'rootView'

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "No email found");
        emailTextView.setText(userEmail);

        // Configurar el botón "Dar de Baja Usuario"
        deleteAccountButton.setOnClickListener(v -> {
            String token = sharedPreferences.getString("access_token", null);

            int userId = sharedPreferences.getInt("user_id", -1);
            Log.d("ProfileFragment", "UserID recuperado de SharedPreferences: " + userId);


            Log.d("ProfileFragment", "Token obtenido: " + token);
            Log.d("ProfileFragment", "UserID obtenido: " + userId);

            if (token != null && userId != -1) {
                deleteUser(token, userId);
            } else {
                Toast.makeText(getContext(), "Error al autenticar. Inicia sesión de nuevo.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

        // Inicializar Retrofit y deleteApi
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-mercado-libro-mobile.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(deleteApi.class);

        return rootView;
    }

    private void deleteUser(String token, Integer userId) {
        Log.d("ProfileFragment", "UserID enviado para eliminación: " + userId);
        Call<Void> call = apiService.deleteUser("Bearer " + token, String.valueOf(userId));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                    Log.e("ProfileFragment", "Error al eliminar cuenta: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("ProfileFragment", "Error en la solicitud de eliminación", t);
            }
        });
    }
}
