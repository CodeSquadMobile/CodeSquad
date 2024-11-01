package com.example.mercadolibromobile.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.PagoApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.Pago;
import com.example.mercadolibromobile.utils.AuthUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PagoFragment extends Fragment {

    private static final String TAG = "PagoFragment";

    private EditText etNumeroTarjeta, etCVV, etVencimiento, etTipoTarjeta;
    private TextView tvNumeroTarjetaMostrar, tvCVVMostrar, tvVencimientoMostrar, tvMostrarTipoTarjeta;
    private Button btnPagar;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagar, container, false);

        // Inicializar vistas
        etNumeroTarjeta = view.findViewById(R.id.etNumeroTarjeta);
        etCVV = view.findViewById(R.id.etCVV);
        etVencimiento = view.findViewById(R.id.etVencimiento);
        etTipoTarjeta = view.findViewById(R.id.etTipoTarjeta);

        tvNumeroTarjetaMostrar = view.findViewById(R.id.tvNumeroTarjetaMostrar);
        tvCVVMostrar = view.findViewById(R.id.tvCVVMostrar);
        tvVencimientoMostrar = view.findViewById(R.id.tvVencimientoMostrar);
        tvMostrarTipoTarjeta = view.findViewById(R.id.tvmostarTipoTarjeta);

        btnPagar = view.findViewById(R.id.btnPagar);

        // Inicializar SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", getActivity().MODE_PRIVATE);

        btnPagar.setOnClickListener(v -> realizarPago());

        return view;
    }

    private void realizarPago() {
        String numeroTarjeta = etNumeroTarjeta.getText().toString().trim();
        String cvv = etCVV.getText().toString().trim();
        String vencimiento = etVencimiento.getText().toString().trim();
        String tipoTarjeta = etTipoTarjeta.getText().toString().trim().toLowerCase();

        String token = "Bearer " + sharedPreferences.getString("access_token", "");

        if (!token.isEmpty()) {
            // Crear instancia del modelo Pago sin el campo usuario
            Pago pago = new Pago(numeroTarjeta, cvv, vencimiento, tipoTarjeta);

            // Configurar Retrofit y PagoApi
            Retrofit retrofit = RetrofitClient.getInstance("https://backend-mercado-libro-mobile.onrender.com/api/");
            PagoApi pagoApi = retrofit.create(PagoApi.class);

            // Realizar la solicitud POST
            pagoApi.realizarPago(token, pago).enqueue(new Callback<Pago>() {
                @Override
                public void onResponse(Call<Pago> call, Response<Pago> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Pago pagoRespuesta = response.body();

                        // Mostrar los datos de la respuesta en los TextViews
                        tvNumeroTarjetaMostrar.setText("Número de Tarjeta: " + pagoRespuesta.getNumero_tarjeta());
                        tvCVVMostrar.setText("CVV: " + pagoRespuesta.getCvv());
                        tvVencimientoMostrar.setText("Vencimiento: " + pagoRespuesta.getVencimiento());
                        tvMostrarTipoTarjeta.setText("Tipo de Tarjeta: " + pagoRespuesta.getTipo_tarjeta());
                        Toast.makeText(getActivity(), "Pago realizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                            Log.e(TAG, "Error al realizar el pago: " + errorMessage);
                            Toast.makeText(getActivity(), "Error al realizar el pago: " + errorMessage, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error al procesar el cuerpo de error", e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Pago> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Fallo al realizar el pago: ", t);
                }
            });
        } else {
            Toast.makeText(getActivity(), "Token no encontrado, por favor inicie sesión.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarDatos(String numeroTarjeta, String cvv, String tipoTarjeta) {
        // Validar número de tarjeta
        if (numeroTarjeta.length() != 16 || !numeroTarjeta.matches("\\d+")) {
            Toast.makeText(getActivity(), "El número de tarjeta debe tener 16 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar CVV
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            Toast.makeText(getActivity(), "El CVV debe tener 3 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar tipo de tarjeta
        if (!tipoTarjeta.equals("debito") && !tipoTarjeta.equals("credito")) {
            Toast.makeText(getActivity(), "El tipo de tarjeta debe ser 'debito' o 'credito'.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;  // Todos los datos son válidos
    }
}
