package com.example.mercadolibromobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mercadolibromobile.api.DireccionApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.Direccion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarDireccionActivity extends AppCompatActivity {

    private EditText editTextCalle, editTextNumero, editTextCiudad, editTextProvincia;
    private Button buttonGuardar, buttonEliminar;
    private String accessToken;
    private Integer direccionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_direcciones);  // Asegúrate de que este layout exista

        // Inicializar vistas
        editTextCalle = findViewById(R.id.editTextCalle);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextCiudad = findViewById(R.id.editTextCiudad);
        editTextProvincia = findViewById(R.id.editTextProvincia);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        // Recuperar el token de autenticación
        SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);

        if (accessToken == null) {
            Toast.makeText(this, "No se encontró un token de autenticación. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Obtener el ID de la dirección para edición
        direccionId = getIntent().getIntExtra("direccion_id", -1);

        // Si es para editar, cargar la dirección existente
        if (direccionId != -1) {
            cargarDireccion();
        } else {
            buttonEliminar.setVisibility(View.GONE);  // Ocultar botón de eliminar si es nuevo
        }

        // Guardar o agregar una nueva dirección
        buttonGuardar.setOnClickListener(v -> guardarDireccion());

        // Eliminar dirección
        buttonEliminar.setOnClickListener(v -> eliminarDireccion());
    }

    private void cargarDireccion() {
        // Inicializar la API con Retrofit
        DireccionApi direccionApi = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/")
                .create(DireccionApi.class);

        // Llamada a la API para obtener la dirección usando el ID
        Call<Direccion> call = direccionApi.getDireccion("Bearer " + accessToken, direccionId);

        call.enqueue(new Callback<Direccion>() {
            @Override
            public void onResponse(@NonNull Call<Direccion> call, @NonNull Response<Direccion> response) {
                if (response.isSuccessful()) {
                    Direccion direccion = response.body();
                    if (direccion != null) {
                        // Rellenar los campos de texto con la información de la dirección
                        editTextCalle.setText(direccion.getCalle());
                        editTextNumero.setText(String.valueOf(direccion.getNumero()));
                        editTextCiudad.setText(direccion.getCiudad());
                        editTextProvincia.setText(direccion.getProvincia());
                    }
                } else {
                    Toast.makeText(EditarDireccionActivity.this, "Error al cargar la dirección", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Direccion> call, @NonNull Throwable t) {
                Toast.makeText(EditarDireccionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarDireccion() {
        // Crear objeto Dirección con los datos ingresados
        Direccion direccion = new Direccion(
                editTextCalle.getText().toString(),
                Integer.parseInt(editTextNumero.getText().toString()),
                editTextCiudad.getText().toString(),
                editTextProvincia.getText().toString()
        );

        // Inicializar API con Retrofit
        DireccionApi direccionApi = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/")
                .create(DireccionApi.class);

        Call<Direccion> call;
        if (direccionId == -1) {  // Si no hay ID, agregar nueva dirección
            call = direccionApi.crearDireccion("Bearer " + accessToken, direccion);
        } else {  // Si hay ID, actualizar dirección existente
            call = direccionApi.actualizarDireccion("Bearer " + accessToken, direccionId, direccion);
        }

        call.enqueue(new Callback<Direccion>() {
            @Override
            public void onResponse(@NonNull Call<Direccion> call, @NonNull Response<Direccion> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarDireccionActivity.this, "Dirección guardada con éxito", Toast.LENGTH_SHORT).show();
                    finish();  // Cerrar la actividad después de guardar
                } else {
                    Toast.makeText(EditarDireccionActivity.this, "Error al guardar la dirección", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Direccion> call, @NonNull Throwable t) {
                Toast.makeText(EditarDireccionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarDireccion() {
        // Inicializar API con Retrofit
        DireccionApi direccionApi = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/")
                .create(DireccionApi.class);

        // Llamada para eliminar la dirección
        Call<Void> call = direccionApi.eliminarDireccion("Bearer " + accessToken, direccionId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarDireccionActivity.this, "Dirección eliminada", Toast.LENGTH_SHORT).show();
                    finish();  // Cerrar la actividad después de eliminar
                } else {
                    Toast.makeText(EditarDireccionActivity.this, "Error al eliminar la dirección", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(EditarDireccionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
