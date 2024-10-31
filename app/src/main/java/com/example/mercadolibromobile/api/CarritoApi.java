package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.ItemCarrito;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface CarritoApi {
    @POST("carrito/")
    Call<ItemCarrito> agregarAlCarrito(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);

    @GET("carrito/")
    Call<List<ItemCarrito>> obtenerCarrito(@Header("Authorization") String token);

    @PUT("carrito/")
    Call<ItemCarrito> actualizarCarrito(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);
}
