package com.example.mercadolibromobile.api;

import com.example.mercadolibromobile.models.Pago;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PagoApi {
    @POST("metodopagos/")
    Call<Pago> realizarPago(@Header("Authorization") String token, @Body Pago pago);
}
