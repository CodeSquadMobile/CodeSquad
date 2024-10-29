package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {
    @GET("usuarios/")
    Call<List<User>> getUsers();

    @DELETE("usuarios/{id}/")
    Call<Void> deleteUser(@Header("Authorization") String token, @Path("id") String userId);
}
