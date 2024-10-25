package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.ApiClient;
import com.example.mercadolibromobile.api.ApiClient.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private SharedPreferences sharedPreferences;
    private String userEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("user_email", "No email found");

        // Mostrar el correo electrónico en el TextView
        emailTextView.setText(userEmail);

        // Configurar el botón "Dar de Baja"
        Button darDeBajaButton = rootView.findViewById(R.id.button9);
        darDeBajaButton.setOnClickListener(v -> {
            // Confirmación de eliminación de cuenta
            mostrarConfirmacionEliminarCuenta();
        });

        return rootView;
    }

    private void mostrarConfirmacionEliminarCuenta() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Eliminación de Cuenta")
                .setMessage("¿Está seguro que desea eliminar su cuenta asociada con el correo: " + userEmail + "?" +" Mira que a freir churros despues...")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> deleteUser(userEmail))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteUser(String userEmail) {
        String token = getAccessToken();
        if (token == null) {
            Toast.makeText(getContext(), "Token de acceso no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + token; // Cambia "Bearer" a "Token" según corresponda

        Call<Void> call = apiService.deleteUser(authToken, userEmail);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Usuario eliminado exitosamente
                    Toast.makeText(getContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    // Aquí puedes redirigir al usuario a otra pantalla o cerrar sesión
                } else {
                    // Manejar el error
                    Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar la falla de la solicitud
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAccessToken() {
        return sharedPreferences.getString("access_token", null);
    }
}
