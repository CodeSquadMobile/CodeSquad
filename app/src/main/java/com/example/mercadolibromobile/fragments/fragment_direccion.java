package com.example.mercadolibromobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class fragment_direccion extends Fragment {

    // Constructor vacío requerido para instancias de fragmento
    public fragment_direccion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_direccion, container, false);

        // Inicializar los elementos de la interfaz
        TextView tvCalleIngresada = view.findViewById(R.id.tvCalleIngresada);
        TextView tvNumeroIngresado = view.findViewById(R.id.tvNumeroIngresado);
        TextView tvProvinciaIngresada = view.findViewById(R.id.tvProvinciaIngresada);

        EditText etCalle = view.findViewById(R.id.etCalle);
        EditText etNumero = view.findViewById(R.id.etNumero);
        EditText etCiudad = view.findViewById(R.id.etCiudad);
        EditText etProvincia = view.findViewById(R.id.etProvincia);

        Button btnFinalizarCompra = view.findViewById(R.id.btnIrAlPago);

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos ingresados
                String calle = etCalle.getText().toString();
                String numero = etNumero.getText().toString();
                String ciudad = etCiudad.getText().toString();
                String provincia = etProvincia.getText().toString();

                // Mostrar los datos ingresados en TextViews
                tvCalleIngresada.setText("Calle ingresada: " + calle);
                tvNumeroIngresado.setText("Número ingresado: " + numero);
                tvProvinciaIngresada.setText("Provincia ingresada: " + provincia);

                // Llamar a la función para ir al fragmento de pago
                irAlPago();
            }
        });

        return view;
    }

    private void irAlPago() {
        // Navegar al fragmento de pago
        FragmentPago pagoFragment = new FragmentPago(); // Asegúrate de que este fragmento esté creado
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment_pago)
                .addToBackStack(null)
                .commit();
    }
}
