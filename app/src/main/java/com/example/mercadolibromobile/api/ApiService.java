package com.example.mercadolibromobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import com.example.mercadolibromobile.models.AuthModels;
import com.example.mercadolibromobile.models.Resena;
import com.example.mercadolibromobile.models.User;

public interface ApiService {
    @GET("usuarios/")
    Call<List<User>> getUsers();

    @POST("/api/auth/login/")
    Call<User> loginUser(@Body AuthModels.LoginRequest loginRequest);

    @GET("/api/resenas/")
    Call<List<Resena>> getResenas(@Header("Authorization") String token);
}
