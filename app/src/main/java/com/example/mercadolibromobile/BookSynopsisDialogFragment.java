package com.example.mercadolibromobile; // Asegúrate de que el paquete sea correcto

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BookSynopsisDialogFragment extends DialogFragment {

    private static final String ARG_DESCRIPTION = "description";

    // Método para crear una nueva instancia del diálogo con un argumento
    public static BookSynopsisDialogFragment newInstance(String description) {
        BookSynopsisDialogFragment fragment = new BookSynopsisDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description); // Pasa la descripción como argumento
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_book_synopsis, container, false);

        TextView tvDescription = view.findViewById(R.id.tvDescription);
        Button btnClose = view.findViewById(R.id.btnClose);

        // Configurar el texto de la sinopsis
        if (getArguments() != null) {
            String description = getArguments().getString(ARG_DESCRIPTION);
            tvDescription.setText(description);
        }

        btnClose.setOnClickListener(v -> dismiss()); // Cierra el diálogo al hacer clic

        return view;
    }
}
