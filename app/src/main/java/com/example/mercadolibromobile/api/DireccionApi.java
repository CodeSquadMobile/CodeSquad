package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Direccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DireccionApi {

    // Obtener direcciones del usuario autenticado usando el token en el encabezado
    @GET("direcciones/")
    Call<List<Direccion>> getDirecciones(@Header("Authorization") String authToken);

    // Crear una nueva dirección
    @POST("direcciones/")
    Call<Direccion> crearDireccion(@Header("Authorization") String authToken, @Body Direccion direccion);

    // Actualizar una dirección existente
    @PUT("direcciones/{id}/")
    Call<Direccion> actualizarDireccion(@Header("Authorization") String authToken, @Path("id") int id, @Body Direccion direccion);

    // Eliminar una dirección
    @DELETE("direcciones/{id}/")
    Call<Void> eliminarDireccion(@Header("Authorization") String authToken, @Path("id") int id);

    // Obtener una dirección específica
    @GET("direcciones/{id}/")
    Call<Direccion> getDireccion(@Header("Authorization") String authToken, @Path("id") int direccionId);
}
