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

    // Parámetros de inicialización opcionales si los necesitas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Constructor vacío requerido
    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout personalizado (activity_contacto.xml)
        View view = inflater.inflate(R.layout.fragment_contact, container, false);


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
