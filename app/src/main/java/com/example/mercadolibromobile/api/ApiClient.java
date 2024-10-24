package com.example.mercadolibromobile.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // Definir una sola URL base
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";

    private static Retrofit retrofit = null;

    // Método para obtener el cliente Retrofit
    public static Retrofit getClient() {
        // Configurar Retrofit con la URL base
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
