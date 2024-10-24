package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Direccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DirApi {
    @GET("direcciones/")
    Call<List<Direccion>> getDirecciones();
}
