package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Direccion;

import java.util.List;
import retrofit2.http.Body;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface DirApi {
    @GET("direcciones/")
    Call<List<Direccion>> getDirecciones(@Header("Authorization") String authHeader);

    @POST("direcciones/")
    Call<Direccion> crearDireccion(@Header("Authorization") String authHeader, @Body Direccion direccion);
}
