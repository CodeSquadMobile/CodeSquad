package com.example.mercadolibromobile.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mercadolibromobile.EditarDireccionActivity;
import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.DireccionApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.Direccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DireccionesActivity extends AppCompatActivity {

    private ListView listViewDirecciones;
    private Button buttonAgregarDireccion;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);

        // Recuperar el token
        SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);

        if (accessToken == null) {
            Toast.makeText(this, "No se encontró token de autenticación. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        listViewDirecciones = findViewById(R.id.listViewDirecciones);
        buttonAgregarDireccion = findViewById(R.id.buttonAgregarDireccion);

        // Cargar las direcciones del usuario
        cargarDirecciones();

        // Botón para agregar nueva dirección
        buttonAgregarDireccion.setOnClickListener(v -> {
            Intent intent = new Intent(DireccionesActivity.this, EditarDireccionActivity.class);
            startActivity(intent);
        });

        // Permitir editar o eliminar direcciones al hacer clic en la lista
        listViewDirecciones.setOnItemClickListener((parent, view, position, id) -> {
            Direccion direccionSeleccionada = (Direccion) parent.getItemAtPosition(position);
            Intent intent = new Intent(DireccionesActivity.this, EditarDireccionActivity.class);
            intent.putExtra("direccion_id", direccionSeleccionada.getId());
            startActivity(intent);
        });
    }

    private void cargarDirecciones() {
        DireccionApi direccionApi = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/")
                .create(DireccionApi.class);

        Call<List<Direccion>> call = direccionApi.getDirecciones("Bearer " + accessToken);
        call.enqueue(new Callback<List<Direccion>>() {
            @Override
            public void onResponse(@NonNull Call<List<Direccion>> call, @NonNull Response<List<Direccion>> response) {
                if (response.isSuccessful()) {
                    List<Direccion> direcciones = response.body();
                    if (direcciones != null) {
                        ArrayAdapter<Direccion> adapter = new ArrayAdapter<>(DireccionesActivity.this, android.R.layout.simple_list_item_1, direcciones);
                        listViewDirecciones.setAdapter(adapter);
                    } else {
                        Toast.makeText(DireccionesActivity.this, "No se encontraron direcciones", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DireccionesActivity.this, "Error al obtener direcciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Direccion>> call, @NonNull Throwable t) {
                Toast.makeText(DireccionesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
