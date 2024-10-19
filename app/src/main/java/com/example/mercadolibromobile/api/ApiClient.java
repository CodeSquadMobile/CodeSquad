package com.example.mercadolibromobile.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//<<<<<<< marcelolunadallalasta
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";
=======
    // Definir las direcciones IP
 //   private static final String[] BASE_URLS = {
   //         "http://192.168.0.50:8000/api/", // Ivette
     //       "http://10.0.2.2:8000/api/",     // Marcelo
       //     "http://192.168.100.26:8000/api/", // Nahir
         //   "http://192.168.0.244:8000/api/", // Leo
           // "http://192.168.0.53:8000/api/"   // Invitado
    };
//>>>>>>> develop

    private static Retrofit retrofit = null;

    // Método para obtener el cliente Retrofit
    public static Retrofit getClient(int index) {
        // Asegurarse de que el índice esté dentro del rango
        if (index < 0 || index >= BASE_URLS.length) {
            throw new IllegalArgumentException("Índice de URL fuera de rango");
        }

        // Reiniciar Retrofit si se selecciona una nueva URL
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URLS[3]) // Usar la URL seleccionada acaaa se pone el indice
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
