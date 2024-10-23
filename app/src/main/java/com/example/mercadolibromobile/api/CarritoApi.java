package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.ItemCarrito;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface CarritoApi {
    @POST("carrito/")
    Call<ItemCarrito> agregarAlCarrito(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);

    @GET("carrito/")
    Call<List<ItemCarrito>> obtenerCarrito(@Header("Authorization") String token);


    @POST("carrito/eliminar/")
    Call<Void> eliminarDelCarrito(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);


    @POST("carrito/actualizar/")
    Call<Void> actualizarCantidad(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);
}
