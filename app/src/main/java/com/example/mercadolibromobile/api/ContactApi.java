package com.example.mercadolibromobile.api;

import android.os.SharedMemory;

import com.example.mercadolibromobile.models.Contacto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ContactApi {
    static SharedMemory getRetrofitInstance() {
        return null;
    }

    @GET("contacto/")
    Call<List<Contacto>> obtenerContactos();

    @POST("contacto/")
    Call<Contacto> crearContacto(@Body Contacto contacto);
}