package com.example.mercadolibromobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout personalizado (activity_contacto.xml)
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Obtén las referencias a los elementos del layout
        EditText consultaEditText = view.findViewById(R.id.etConsulta);
        Button enviarConsultaButton = view.findViewById(R.id.btnEnviarConsulta);

        // Establece el comportamiento del botón "Enviar consulta"
        enviarConsultaButton.setOnClickListener(v -> {
            String consulta = consultaEditText.getText().toString().trim(); // Eliminamos espacios adicionales

            // Validación: el campo no debe estar vacío
            if (consulta.isEmpty()) {
                consultaEditText.setError("Por favor, escribe tu consulta."); // Mensaje de error claro
                consultaEditText.requestFocus(); // Enfocar el campo de consulta
            } else if (consulta.length() < 10) { // Validación adicional: consulta demasiado corta
                consultaEditText.setError("La consulta debe tener al menos 10 caracteres.");
                consultaEditText.requestFocus();
            } else {
                // Lógica para manejar el envío de la consulta si los datos son válidos
                Toast.makeText(getActivity(), "Consulta enviada: " + consulta, Toast.LENGTH_SHORT).show();
                consultaEditText.setText(""); // Limpiar el campo después del envío
            }
        });

        return view;
    }
}

