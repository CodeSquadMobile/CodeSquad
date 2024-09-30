package com.example.mercadolibromobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class fragment_Finalizar extends Fragment {

    // TableLayout to display the list of books
    private TableLayout tableLayout;

    // Button to finalize the purchase
    private Button finalizarButton;

    // List of books retrieved from the backend or cart
    private List<Libro> listaLibros;

    // Empty constructor required for fragments
    public fragment_Finalizar() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the list of books from the backend or cart
        listaLibros = obtenerLibrosDelCarrito();  // Método que se conecta al backend/carrito
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__finalizar, container, false);

        // Reference the table and button
        tableLayout = view.findViewById(R.id.tableOverview);
        finalizarButton = view.findViewById(R.id.button4);

        // Dynamically add rows to the table with the list of books
        for (Libro libro : listaLibros) {
            addProductToTable(libro.getNombre(), String.valueOf(libro.getCantidad()), "$" + libro.getPrecio());
        }

        // Set the action for the "Finalizar compra" button
        finalizarButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Compra finalizada", Toast.LENGTH_SHORT).show();
            // Here would go the logic to process the purchase
        });

        return view;
    }

    // Método para agregar productos a la tabla
    private void addProductToTable(String nombre, String cantidad, String precio) {
        TableRow row = new TableRow(getContext());

        TextView nombreView = new TextView(getContext());
        nombreView.setText(nombre);
        nombreView.setGravity(Gravity.CENTER); // Centra el texto
        nombreView.setBackgroundResource(R.drawable.border); // Agrega un borde
        nombreView.setTextColor(getResources().getColor(R.color.crim));

        TextView cantidadView = new TextView(getContext());
        cantidadView.setText(cantidad);
        cantidadView.setGravity(Gravity.CENTER); // Centra el texto
        cantidadView.setBackgroundResource(R.drawable.border); // Agrega un borde
        cantidadView.setTextColor(getResources().getColor(R.color.crim));

        TextView precioView = new TextView(getContext());
        precioView.setText(precio);
        precioView.setGravity(Gravity.CENTER); // Centra el texto
        precioView.setBackgroundResource(R.drawable.border); // Agrega un borde
        precioView.setTextColor(getResources().getColor(R.color.crim));

        // Add TextViews to the row
        row.addView(nombreView);
        row.addView(cantidadView);
        row.addView(precioView);

        // Add row to the table
        tableLayout.addView(row);
    }

    // Simulation of the method to retrieve books from the cart or backend
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