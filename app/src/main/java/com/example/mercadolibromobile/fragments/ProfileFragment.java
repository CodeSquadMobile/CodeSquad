package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.activities.MisResenasActivity; // Asegúrate de importar la actividad

public class ProfileFragment extends Fragment {

    private TextView emailTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "No email found");

        // Mostrar el correo electrónico en el TextView
        emailTextView.setText(userEmail);

        // Configurar el botón "Mis Reseñas"
        Button reviewsButton = rootView.findViewById(R.id.button2);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir MisResenasActivity
                Intent intent = new Intent(getActivity(), MisResenasActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        return rootView;
    }
}
