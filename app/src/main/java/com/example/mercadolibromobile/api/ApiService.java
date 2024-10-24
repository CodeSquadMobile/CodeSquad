package com.example.mercadolibromobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.mercadolibromobile.models.User;

public interface ApiService {
    @GET("usuarios/")
    Call<List<User>> getUsers();
}
