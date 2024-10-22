package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookApi {
    @GET("libros/")
    Call<List<Book>> getBooks();

    // Nuevo método para obtener un libro por su ID
    @GET("libros/{id}/")
    Call<Book> getBookById(@Path("id") int id);
}
