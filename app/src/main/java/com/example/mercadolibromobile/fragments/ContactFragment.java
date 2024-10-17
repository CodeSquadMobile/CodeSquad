package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.ContactApi;
import com.example.mercadolibromobile.models.Contacto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactFragment extends Fragment {

    private ContactApi contactApi;
    private EditText nombreEditText, asuntoEditText, emailEditText, consultaEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout personalizado (activity_contacto.xml)
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/contacto/") // Coloca aquí la URL base de tu backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        contactApi = retrofit.create(ContactApi.class);

        // Referencias a los elementos del layout
        nombreEditText = view.findViewById(R.id.etNombre);
        asuntoEditText = view.findViewById(R.id.etAsunto);
        emailEditText = view.findViewById(R.id.etEmail);
        consultaEditText = view.findViewById(R.id.etConsulta);
        Button enviarConsultaButton = view.findViewById(R.id.btnEnviarConsulta);

        // Establecer comportamiento del botón "Enviar consulta"
        enviarConsultaButton.setOnClickListener(v -> {
            String nombre = nombreEditText.getText().toString().trim();
            String asunto = asuntoEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String consulta = consultaEditText.getText().toString().trim();

            // Validación
            if (asunto.isEmpty()) {
                asuntoEditText.setError("Por favor, escribe el asunto.");
                asuntoEditText.requestFocus();
            } else if (nombre.isEmpty()) {
                nombreEditText.setError("Por favor, escribe tu nombre.");
                nombreEditText.requestFocus();
            } else if (email.isEmpty() || !email.contains("@")) {
                emailEditText.setError("Por favor, escribe un email válido.");
                emailEditText.requestFocus();
            } else if (consulta.isEmpty()) {
                consultaEditText.setError("Por favor, escribe tu consulta.");
                consultaEditText.requestFocus();
            } else if (consulta.length() < 10) {
                consultaEditText.setError("La consulta debe tener al menos 10 caracteres.");
                consultaEditText.requestFocus();
            } else {
                // Crear un nuevo contacto
                Contacto nuevoContacto = new Contacto(nombre, email, asunto, consulta);
                enviarConsulta(nuevoContacto);
            }
        });

        return view;
    }

    private void enviarConsulta(Contacto contacto) {
        Call<Contacto> call = contactApi.crearContacto(contacto);
        call.enqueue(new Callback<Contacto>() {
            @Override
            public void onResponse(@NonNull Call<Contacto> call, @NonNull Response<Contacto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Consulta enviada con éxito.", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(getActivity(), "Error al enviar la consulta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Contacto> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para obtener contactos, si fuera necesario
    private void obtenerContactos() {
        Call<List<Contacto>> call = contactApi.obtenerContactos();
        call.enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contacto>> call, @NonNull Response<List<Contacto>> response) {
                if (response.isSuccessful()) {
                    List<Contacto> contactos = response.body();
                    // Manejar la lista de contactos aquí, por ejemplo mostrarla en un RecyclerView
                } else {
                    Toast.makeText(getActivity(), "Error al obtener los contactos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        // Limpiar los campos de entrada
        nombreEditText.setText("");
        asuntoEditText.setText("");
        emailEditText.setText("");
        consultaEditText.setText("");
    }
}
