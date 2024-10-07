package com.example.mercadolibromobile.api;
import com.example.mercadolibromobile.models.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface BookApi {
    @GET("libros/")
    Call<List<Book>> getBooks();
}

