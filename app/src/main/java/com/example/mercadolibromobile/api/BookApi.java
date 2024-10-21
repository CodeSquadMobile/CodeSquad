package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApi {
    @GET("libros/")
    Call<List<Book>> getBooks(@Query("titulo") String titulo, @Query("id_categoria__nombre_categoria") String categoria);

    // Nuevo método para obtener un libro por su ID
    @GET("libros/{id}/")
    Call<Book> getBookById(@Path("id") int id);
}
