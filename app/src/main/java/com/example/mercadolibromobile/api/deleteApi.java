// deleteApi.java
package com.example.mercadolibromobile.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface deleteApi {
    @DELETE("usuarios/{user_id}/")
    Call<Void> deleteUser(@Header("Authorization") String authToken, @Path("user_id") String userId);
}
