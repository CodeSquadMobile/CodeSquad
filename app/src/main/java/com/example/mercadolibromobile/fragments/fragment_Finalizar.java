package com.example.mercadolibromobile.fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mercadolibromobile.api.BookApi;
import com.example.mercadolibromobile.api.CarritoApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.adapters.CarritoAdapter;
import com.example.mercadolibromobile.models.Book;
import com.example.mercadolibromobile.models.ItemCarrito;
import com.example.mercadolibromobile.R;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.api.DireccionApi;
import com.example.mercadolibromobile.models.Direccion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_Finalizar extends Fragment {


    private RecyclerView recyclerViewCarrito;
    private CarritoAdapter carritoAdapter;
    private Button btnFinalizarCompra;
    private List<ItemCarrito> itemsCarrito = new ArrayList<>();
    private static final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";
    private static final String TAG = "Fragment_Finalizar";

  

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finalizar, container, false);


   
        obtenerCarrito();

        btnFinalizarCompra.setOnClickListener(v -> finalizarCompra());


        return view;
    }

    private void actualizarVistaLibros() {
        contenedorLibros.removeAllViews();
        double precioTotal = 0.0;

        for (Libro libro : listaLibros) {
            precioTotal += agregarProducto(libro);
        }

        precioTotalTextView.setText("Total: $" + String.format("%.2f", precioTotal));
    }


        call.enqueue(new Callback<List<ItemCarrito>>() {
            @Override
            public void onResponse(Call<List<ItemCarrito>> call, Response<List<ItemCarrito>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemsCarrito = response.body();
                    getBooks(); // Llamar a getBooks después de obtener itemsCarrito
                } else {
                    Log.e(TAG, "Error al obtener el carrito. Código de respuesta: " + response.code());
                    Toast.makeText(getContext(), "Error al obtener el carrito.", Toast.LENGTH_SHORT).show();
                }
            }


        LinearLayout layoutHorizontal = new LinearLayout(getContext());
        layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        layoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layoutHorizontal.setPadding(16, 16, 16, 16);

        // Detalles del libro (nombre, cantidad y precio)
        LinearLayout layoutDetalles = new LinearLayout(getContext());
        layoutDetalles.setOrientation(LinearLayout.VERTICAL);
        layoutDetalles.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Esto permite que los detalles ocupen la mayor parte del espacio
        ));

        // Texto con información del libro
        TextView nombreView = new TextView(getContext());
        nombreView.setText(String.format("Libro: %s", libro.getNombre()));
        nombreView.setTextSize(16);
        nombreView.setTextColor(getResources().getColor(R.color.black));

        TextView cantidadView = new TextView(getContext());
        cantidadView.setText("Cantidad: " + libro.getCantidad());
        cantidadView.setTextSize(16);
        cantidadView.setTextColor(getResources().getColor(R.color.black));

        // precio, iconos
        LinearLayout layoutPrecioIconos = new LinearLayout(getContext());
        layoutPrecioIconos.setOrientation(LinearLayout.HORIZONTAL);
        layoutPrecioIconos.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layoutPrecioIconos.setWeightSum(1); // Para usar pesos en la distribución

        TextView precioView = new TextView(getContext());
        precioView.setText("Precio: $" + String.format("%.2f", libro.getPrecio()));
        precioView.setTextSize(16);
        precioView.setTextColor(getResources().getColor(R.color.black));

        // Añadir precio al layoutPrecioIconos
        layoutPrecioIconos.addView(precioView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.6f)); // Peso 0.6

        LinearLayout layoutIconos = new LinearLayout(getContext());
        layoutIconos.setOrientation(LinearLayout.HORIZONTAL);
        layoutIconos.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.4f
        ));
        layoutIconos.setGravity(Gravity.END); // Alinear los iconos al final

        ImageView sumarIcon = new ImageView(getContext());
        sumarIcon.setImageResource(R.drawable.ic_sumar); // icono vector
        sumarIcon.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        sumarIcon.setPadding(16, 0, 0, 0); // Espacio entre el texto y el icono
        sumarIcon.setOnClickListener(v -> {
            libro.setCantidad(libro.getCantidad() + 1);
            cantidadView.setText("Cantidad: " + libro.getCantidad());
            actualizarPrecioTotal();
        });

        ImageView restarIcon = new ImageView(getContext());
        restarIcon.setImageResource(R.drawable.ic_restar); // icono de resta
        restarIcon.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        restarIcon.setPadding(16, 0, 0, 0); // Espacio entre los iconos
        restarIcon.setOnClickListener(v -> {
            if (libro.getCantidad() > 1) {
                libro.setCantidad(libro.getCantidad() - 1);
                cantidadView.setText("Cantidad: " + libro.getCantidad());
                actualizarPrecioTotal();
            } else {
                Toast.makeText(getContext(), "No se puede reducir la cantidad a menos de 1", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView eliminarIcon = new ImageView(getContext());
        eliminarIcon.setImageResource(R.drawable.ic_borrar); // icono de borrar
        eliminarIcon.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        eliminarIcon.setPadding(16, 0, 0, 0); // Espacio entre los iconos
        eliminarIcon.setOnClickListener(v -> {
            contenedorLibros.removeView(cardView);
            listaLibros.remove(libro); // Asegúrate de eliminar el libro de la lista
            actualizarPrecioTotal();
            Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
        });

        // Añadir iconos al layoutIconos
        layoutIconos.addView(sumarIcon);
        layoutIconos.addView(restarIcon);
        layoutIconos.addView(eliminarIcon);

        // Añadir layoutIconos al layoutPrecioIconos
        layoutPrecioIconos.addView(layoutIconos); // Añadir layoutIconos a la derecha

        // Añadir nombre y cantidad al layoutDetalles
        layoutDetalles.addView(nombreView);
        layoutDetalles.addView(cantidadView);
        layoutDetalles.addView(layoutPrecioIconos); // Añadir layoutPrecioIconos al final de los detalles

        // Añadir detalles y los iconos al layout principal horizontal
        layoutHorizontal.addView(layoutDetalles);

        // Añadir el layout horizontal al CardView
        cardView.addView(layoutHorizontal);
        contenedorLibros.addView(cardView);

        return libro.getCantidad() * libro.getPrecio(); // Retorna el precio total del libro
    }

    private List<Libro> obtenerLibrosDelCarrito() {
        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("Harry Potter", 2, 20.99));
        libros.add(new Libro("The Lord of the Rings", 1, 15.99));
        libros.add(new Libro("Pride and Prejudice", 3, 12.99));
        return libros;
    }

    private void actualizarPrecioTotal() {
        double precioTotal = 0.0;
        for (Libro libro : listaLibros) {
            precioTotal += libro.getCantidad() * libro.getPrecio();
        }
        precioTotalTextView.setText("Total: $" + String.format("%.2f", precioTotal));
    }



    private void getBooks() {
        BookApi bookApi = RetrofitClient.getInstance(BASE_URL).create(BookApi.class);
        Call<List<Book>> call = bookApi.getBooks();


        // Recuperar el token de SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth", getContext().MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", null);

        if (accessToken == null) {
            Toast.makeText(getActivity(), "No se encontró un token de autenticación", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicializar Retrofit con la URL fija
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear la instancia de la API
        DireccionApi direccionApi = retrofit.create(DireccionApi.class);

        // Realizar la llamada para obtener las direcciones con el token en el encabezado
        Call<List<Direccion>> call = direccionApi.getDirecciones("Bearer " + accessToken);

        // Manejo de la respuesta de la llamada
        call.enqueue(new Callback<List<Direccion>>() {
            @Override

            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> libros = response.body();
                    configurarAdapter(itemsCarrito, libros);

           
                } else {
                    Toast.makeText(getActivity(), "Error al obtener direcciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(TAG, "Fallo la conexión al obtener los libros: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void finalizarCompra() {
        Fragment direccionFragment = new fragment_direccion();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, direccionFragment)
                .addToBackStack(null)
                .commit();
    }

    private String getAccessToken() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("access_token", null);
    }
}

