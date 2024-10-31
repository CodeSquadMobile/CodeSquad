package com.example.mercadolibromobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.Resena;

import java.util.List;

public class ResenaAdapter extends RecyclerView.Adapter<ResenaAdapter.ResenaViewHolder> {

    private List<Resena> resenas;

    public ResenaAdapter(List<Resena> resenas) {
        this.resenas = resenas;
    }

    @NonNull
    @Override
    public ResenaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout item_resena.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resena, parent, false);
        return new ResenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResenaViewHolder holder, int position) {
        Resena resena = resenas.get(position);
        holder.commentTextView.setText(resena.getComentario());
        holder.dateTextView.setText(resena.getFechaCreacion());
        holder.libroTextView.setText("Libro ID: " + resena.getLibro()); // Mostrar solo el ID del libro
        holder.libroTextView.setText("Título: " + resena.getTituloLibro());
        holder.usuarioTextView.setText("Usuario: " + resena.getEmailUsuario());
    }

    @Override
    public int getItemCount() {
        return resenas.size();
    }

    // Método para actualizar la lista de reseñas
    public void updateResenas(List<Resena> newResenas) {
        this.resenas.clear(); // Limpiar la lista actual
        this.resenas.addAll(newResenas); // Agregar las nuevas reseñas
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }

    static class ResenaViewHolder extends RecyclerView.ViewHolder {
        TextView commentTextView;
        TextView dateTextView;
        TextView libroTextView;  // Añadir declaración de libroTextView
        TextView usuarioTextView; // Añadir declaración de usuarioTextView

        ResenaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar los TextViews desde el layout
            commentTextView = itemView.findViewById(R.id.comentarioTextView);
            dateTextView = itemView.findViewById(R.id.fechaTextView);
            libroTextView = itemView.findViewById(R.id.libroTextView); // Inicializar libroTextView
            usuarioTextView = itemView.findViewById(R.id.usuarioTextView); // Inicializar usuarioTextView
        }
    }
}
