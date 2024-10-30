package com.example.mercadolibromobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mercadolibromobile.api.BookApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResenasActivity extends AppCompatActivity {

    private Spinner spinnerOpciones;
    private BookApi bookApi;
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresena);
        // Aquí puedes inicializar tu vista y lógica para agregar reseñas

        spinnerOpciones = findViewById(R.id.spinnerOpciones);
        bookApi = RetrofitClient.getInstance(BASE_URL).create(BookApi.class); // Asumiendo que tienes una clase RetrofitClient para la configuración

        // Obtener libros
        fetchBooks();
    }
    private void fetchBooks() {
        bookApi.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    setupSpinner(books);
                } else {
                    Toast.makeText(AddResenasActivity.this, "Error al obtener libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(AddResenasActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinner(List<Book> books) {
        // Extraer los títulos de los libros
        String[] bookTitles = new String[books.size()];
        for (int i = 0; i < books.size(); i++) {
            bookTitles[i] = books.get(i).getTitulo(); // Asegúrate de que el getter es correcto
        }

        // Crear un adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOpciones.setAdapter(adapter);
    }
}
