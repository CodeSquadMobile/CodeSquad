package com.example.mercadolibromobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
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

    private void addProductToTable(String nombre, String cantidad, String precio) {
        final TableRow row = new TableRow(getContext());

        // Añadir un borde solo a la fila (esto será el borde externo)
        row.setBackgroundResource(R.drawable.border);

        // Configuración de la celda "Nombre" sanitizada con escapeHTML para cyberSec
        TextView nombreView = new TextView(getContext());
        String nombreS = Html.escapeHtml(nombre);
        nombreView.setText(nombreS);
        nombreView.setGravity(Gravity.CENTER);
        nombreView.setPadding(8, 8, 8, 8);
        nombreView.setTextColor(getResources().getColor(R.color.crim));
        nombreView.setBackgroundResource(R.drawable.border_vertical);  // Borde vertical entre las celdas

        // Configuración del campo modificable "Cantidad" no hace falta sanitizar porque es formato solo numeros
        final EditText cantidadView = new EditText(getContext());
        cantidadView.setText(cantidad);
        cantidadView.setGravity(Gravity.CENTER);
        cantidadView.setPadding(8, 8, 8, 8);
        cantidadView.setInputType(InputType.TYPE_CLASS_NUMBER);
        cantidadView.setEms(3);
        cantidadView.setTextSize(14);
        cantidadView.setTextColor(getResources().getColor(android.R.color.black));  // Cambiar color del texto a negro
        cantidadView.setBackgroundResource(R.drawable.border_vertical);  // Borde vertical entre las celdas

        cantidadView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Integer.parseInt(s.toString()) == 0) {
                    tableLayout.removeView(row); // Elimina la fila si la cantidad es 0
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Configuración de la celda "Precio" sanitizada con escapeHTML para cyberSec
        TextView precioView = new TextView(getContext());
        String precioS = Html.escapeHtml(precio);
        precioView.setText(precioS);
        precioView.setGravity(Gravity.CENTER);
        precioView.setPadding(8, 8, 8, 8);
        precioView.setTextColor(getResources().getColor(R.color.crim));
        precioView.setBackgroundResource(R.drawable.border_vertical);  // Borde vertical entre las celdas

        // Añadir las vistas a la fila
        row.addView(nombreView);
        row.addView(cantidadView);
        row.addView(precioView);

        // Añadir la fila a la tabla
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
    private String nombreS;
    private int cantidad;
    private double precioS;

    public Libro(String nombreS, int cantidad, double precioS) {
        this.nombreS = nombreS;
        this.cantidad = cantidad;
        this.precioS = precioS;
    }

    public String getNombre() {
        return nombreS;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precioS;
    }
}