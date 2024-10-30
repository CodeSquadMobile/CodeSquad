package com.example.mercadolibromobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.ApiService;

public class ProfileFragment extends Fragment {
    private TextView emailTextView;
    private Button deleteAccountButton;
    private ApiService userService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencia al TextView del email
        emailTextView = rootView.findViewById(R.id.textView9);

        // Obtener el correo electrónico desde SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "No email found");
        emailTextView.setText(userEmail);

        // Configurar el botón "Dar de Baja Usuario"
        deleteAccountButton = rootView.findViewById(R.id.button9);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String accessToken = getAccessToken();
//                deleteUser(accessToken);
            }
        });

        return rootView;
    }
}