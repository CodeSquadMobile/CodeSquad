// CarritoApi.java
package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.ItemCarrito;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface CarritoApi {
    @POST("carrito/")
    Call<ItemCarrito> agregarItemAlCarrito(@Body ItemCarrito itemCarrito);

    @GET("carrito/")
    Call<List<ItemCarrito>> obtenerItemsCarrito();
}
