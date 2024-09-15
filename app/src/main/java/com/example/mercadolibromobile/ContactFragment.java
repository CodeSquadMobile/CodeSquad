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
        View view = inflater.inflate(R.layout.activity_main, container, false);

        // Obtén las referencias a los elementos del layout
        EditText consultaEditText = view.findViewById(R.id.etConsulta);
        Button enviarConsultaButton = view.findViewById(R.id.btnEnviarConsulta);

        // Establece el comportamiento del botón "Enviar consulta"
        enviarConsultaButton.setOnClickListener(v -> {
            String consulta = consultaEditText.getText().toString();
            if (!consulta.isEmpty()) {
                // Lógica para manejar el envío de la consulta
                Toast.makeText(getActivity(), "Consulta enviada: " + consulta, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Por favor, escribe tu consulta.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
