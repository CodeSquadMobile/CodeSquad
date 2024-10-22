package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.ItemCarrito;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CarritoApi {
    @POST("carrito/")
    Call<ItemCarrito> agregarAlCarrito(@Header("Authorization") String token, @Body ItemCarrito itemCarrito);

    // Método para obtener los libros del carrito
    @GET("carrito/")
    Call<List<ItemCarrito>> obtenerCarrito(@Header("Authorization") String token);  // Agregar este método
}
