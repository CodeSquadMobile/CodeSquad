package com.example.mercadolibromobile.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class ApiClient {

    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface ApiService {
        @DELETE("users/delete/{email}/")
        Call<Void> deleteUser(@Header("Authorization") String token, @Path("email") String userEmail);
    }
}
