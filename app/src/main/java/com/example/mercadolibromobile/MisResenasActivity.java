package com.example.mercadolibromobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.adapters.ResenaAdapter;
import com.example.mercadolibromobile.api.ApiService;
import com.example.mercadolibromobile.models.Resena;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;

public class MisResenasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResenaAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_resenas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Asignar un adaptador vacío al inicio
        adapter = new ResenaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Inicializar Retrofit y apiService antes de llamar a getResenas()
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-mercado-libro-mobile.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);

        // Realizar la llamada a la API
        getResenas();
    }

    private void getResenas() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", null);
        String emailUsuario = sharedPreferences.getString("user_email", null);

        if (token != null && emailUsuario != null) {
            Call<List<Resena>> call = apiService.getResenas("Bearer " + token);
            call.enqueue(new Callback<List<Resena>>() {
                @Override
                public void onResponse(Call<List<Resena>> call, Response<List<Resena>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Resena> allResenas = response.body();
                        List<Resena> userResenas = new ArrayList<>();

                        // Filtrar las reseñas del usuario logueado
                        for (Resena resena : allResenas) {
                            if (resena.getEmailUsuario().equals(emailUsuario)) {
                                userResenas.add(resena);
                            }
                        }

                        // Actualizar el adaptador con las reseñas del usuario
                        adapter = new ResenaAdapter(userResenas);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MisResenasActivity.this, "No se encontraron reseñas.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Resena>> call, Throwable t) {
                    Log.e("MisResenasActivity", "Error al obtener reseñas", t);
                    Toast.makeText(MisResenasActivity.this, "Error al obtener reseñas.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No se encontró el token o el correo del usuario. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            // Puedes agregar un intent para redirigir al usuario al LoginActivity
        }
    }
}
