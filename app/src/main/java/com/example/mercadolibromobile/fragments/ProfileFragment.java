package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.activities.MisResenasActivity;
import com.example.mercadolibromobile.api.deleteApi;  // Importar la interfaz deleteApi

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "No email found");

        // Actualizar la clave del ID de usuario a "user_id"
        int userId = sharedPreferences.getInt("user_id", 0);  // Revisa si el ID es válido
        String token = sharedPreferences.getString("access_token", null);  // Revisa si el token está presente

        Log.d("ProfileFragment", "User ID: " + userId);
        Log.d("ProfileFragment", "Token: " + token);

        // Mostrar el correo electrónico en el TextView
        emailTextView.setText(userEmail);

        // Configurar el botón "Mis Reseñas"
        Button reviewsButton = rootView.findViewById(R.id.button2);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir MisResenasActivity
                Intent intent = new Intent(getActivity(), MisResenasActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        // Configurar el botón "Dar de baja usuario"
        Button deleteUserButton = rootView.findViewById(R.id.button9);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != 0 && token != null) {
                    eliminarUsuario(userId, "Bearer " + token);
                } else {
                    Log.d("ProfileFragment", "No se puede eliminar el usuario: token o ID inválido");
                }
            }
        });

        return rootView;
    }

    private void eliminarUsuario(int userId, String authToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-mercado-libro-mobile.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deleteApi apiService = retrofit.create(deleteApi.class);  // Instanciar deleteApi
        Call<Void> call = apiService.deleteUser(userId, authToken);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("ProfileFragment", "Usuario eliminado con éxito");
                    // Opcional: Redirigir al usuario a la pantalla de inicio de sesión o cerrar sesión
                } else {
                    Log.d("ProfileFragment", "Error al eliminar usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ProfileFragment", "Fallo en la llamada: " + t.getMessage());
            }
        });
    }
}
