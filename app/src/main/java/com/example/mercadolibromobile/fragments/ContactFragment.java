package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout personalizado (activity_contacto.xml)
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Obtén las referencias a los elementos del layout
        EditText nombreEditText = view.findViewById(R.id.etNombre);
        EditText asuntoEditText = view.findViewById(R.id.etAsunto);
        EditText emailEditText = view.findViewById(R.id.etEmail);
        EditText consultaEditText = view.findViewById(R.id.etConsulta);
        Button enviarConsultaButton = view.findViewById(R.id.btnEnviarConsulta);

        // Establece el comportamiento del botón "Enviar consulta"
        enviarConsultaButton.setOnClickListener(v -> {
            String nombre = nombreEditText.getText().toString().trim();
            String asunto = asuntoEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String consulta = consultaEditText.getText().toString().trim();

            // Validación: los campos no deben estar vacíos
            if  (asunto.isEmpty()) {
                asuntoEditText.setError("Por favor, escribe el asunto.");
                asuntoEditText.requestFocus();
            }   else if(nombre.isEmpty()) {
                nombreEditText.setError("Por favor, escribe tu nombre.");
                nombreEditText.requestFocus();
            }   else if (email.isEmpty() || !email.contains("@")) {
                emailEditText.setError("Por favor, escribe un email válido.");
                emailEditText.requestFocus();
            }   else if (consulta.isEmpty()) {
                consultaEditText.setError("Por favor, escribe tu consulta.");
                consultaEditText.requestFocus();
            }   else if (consulta.length() < 10) {
                consultaEditText.setError("La consulta debe tener al menos 10 caracteres.");
                consultaEditText.requestFocus();
            }   else {
                // Lógica para manejar el envío de la consulta si los datos son válidos
                Toast.makeText(getActivity(), "Consulta enviada: " + consulta, Toast.LENGTH_SHORT).show();
                nombreEditText.setText("");
                asuntoEditText.setText("");
                emailEditText.setText("");
                consultaEditText.setText("");
            }
        });

        return view;
    }
}