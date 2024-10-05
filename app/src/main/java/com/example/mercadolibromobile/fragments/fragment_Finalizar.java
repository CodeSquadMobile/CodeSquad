package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_Finalizar extends Fragment {

    private LinearLayout contenedorLibros;
    private Button finalizarButton;
    private List<Libro> listaLibros;

    public fragment_Finalizar() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaLibros = obtenerLibrosDelCarrito();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__finalizar, container, false);

        contenedorLibros = view.findViewById(R.id.contenedorLibros);
        finalizarButton = view.findViewById(R.id.button4);
        TextView precioTotalTextView = view.findViewById(R.id.precioTotal);

        double precioTotal = 0.0;

        for (Libro libro : listaLibros) {
            agregarProducto(libro.getNombre(), libro.getCantidad(), libro.getPrecio());
            precioTotal += libro.getCantidad() * libro.getPrecio();
        }

        precioTotalTextView.setText("Total: $" + String.format("%.2f", precioTotal));

        finalizarButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Compra finalizada", Toast.LENGTH_SHORT).show();
            // logica de compra
        });

        return view;
    }

    private void agregarProducto(String nombre, int cantidad, double precio) {
        CardView cardView = new CardView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(8);
        cardView.setCardElevation(4);

        // color del card
        cardView.setCardBackgroundColor(getResources().getColor(R.color.beige_transparente));

        LinearLayout layoutLibro = new LinearLayout(getContext());
        layoutLibro.setOrientation(LinearLayout.VERTICAL);
        layoutLibro.setPadding(16, 16, 16, 16);

        TextView nombreView = new TextView(getContext());
        nombreView.setText("Libro: " + nombre);
        nombreView.setTextSize(16);
        nombreView.setTextColor(getResources().getColor(R.color.black)); // color del texto

        TextView cantidadView = new TextView(getContext());
        cantidadView.setText("Cantidad: " + cantidad);
        cantidadView.setTextSize(16);
        cantidadView.setTextColor(getResources().getColor(R.color.black)); // color del texto

        TextView precioView = new TextView(getContext());
        precioView.setText("Precio: $" + String.format("%.2f", precio));
        precioView.setTextSize(16);
        precioView.setTextColor(getResources().getColor(R.color.black)); //  color del texto

        layoutLibro.addView(nombreView);
        layoutLibro.addView(cantidadView);
        layoutLibro.addView(precioView);

        cardView.addView(layoutLibro);
        contenedorLibros.addView(cardView);
    }

    private List<Libro> obtenerLibrosDelCarrito() {
        List<Libro> libros = new ArrayList<>();

        libros.add(new Libro("Harry Potter", 2, 20.99));
        libros.add(new Libro("The Lord of the Rings", 1, 15.99));
        libros.add(new Libro("Pride and Prejudice", 3, 12.99));

        return libros;
    }
}

class Libro {
    private String nombre;
    private int cantidad;
    private double precio;

    public Libro(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }
}

