package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mercadolibromobile.R;

public class fragment_direccion extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_direccion, container, false);

        Button btnIrAPagar = view.findViewById(R.id.btnIrAlPago); // Asegúrate de que el ID coincida
        btnIrAPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAPagar(); // Llama al método para ir al fragmento de pago
            }
        });

        return view;
    }

    private void irAPagar() {
        // Navegar al fragmento de pago
        fragment_pago pagoFragment = new fragment_pago();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, pagoFragment) // Asegúrate de que el fragmento se pase aquí
                .addToBackStack(null)
                .commit();
    }
}
