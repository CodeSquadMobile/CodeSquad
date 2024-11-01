package com.example.mercadolibromobile.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface deleteApi {
    @DELETE("usuarios/{id}/")
    Call<Void> deleteUser(
            @Path("id") int userId,
            @Header("Authorization") String authToken
    );
}