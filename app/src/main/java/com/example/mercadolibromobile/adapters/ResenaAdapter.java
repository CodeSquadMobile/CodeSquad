package com.example.mercadolibromobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadolibromobile.R;
import com.example.mercadolibromobile.models.Resena;

import java.util.List;

public class ResenaAdapter extends RecyclerView.Adapter<ResenaAdapter.ResenaViewHolder> {

    private List<Resena> resenas;
    private OnResenaDeleteListener deleteListener;  // Añadir interfaz de escucha

    // Constructor que recibe la lista de reseñas y el listener de eliminación
    public ResenaAdapter(List<Resena> resenas, OnResenaDeleteListener deleteListener) {
        this.resenas = resenas;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ResenaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resena, parent, false);
        return new ResenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResenaViewHolder holder, int position) {
        Resena resena = resenas.get(position);
        holder.commentTextView.setText(resena.getComentario());
        holder.dateTextView.setText(resena.getFechaCreacion());
        holder.libroTextView.setText("Título: " + resena.getTituloLibro());
        holder.usuarioTextView.setText("Usuario: " + resena.getEmailUsuario());

        // Configurar el listener para el botón de eliminar
        holder.deleteButton.setOnClickListener(v -> {
            eliminarResena(position);
        });
    }

    @Override
    public int getItemCount() {
        return resenas.size();
    }

    // Método para eliminar la reseña y llamar al listener
    public void eliminarResena(int position) {
        Resena resenaEliminada = resenas.get(position);
        resenas.remove(position);  // Eliminar la reseña de la lista
        notifyItemRemoved(position);  // Notificar el cambio en el adaptador

        // Llamar al listener de eliminación para manejar la acción en la actividad
        if (deleteListener != null) {
            deleteListener.onResenaDelete(resenaEliminada);
        }
    }

    // Método para actualizar la lista de reseñas
    public void updateResenas(List<Resena> nuevasResenas) {
        this.resenas.clear();           // Limpiar la lista actual
        this.resenas.addAll(nuevasResenas); // Agregar las nuevas reseñas
        notifyDataSetChanged();         // Notificar el cambio en el adaptador
    }

    // Interfaz para notificar la eliminación de una reseña
    public interface OnResenaDeleteListener {
        void onResenaDelete(Resena resena);
    }

    static class ResenaViewHolder extends RecyclerView.ViewHolder {
        TextView commentTextView, dateTextView, libroTextView, usuarioTextView;
        ImageView deleteButton;

        ResenaViewHolder(@NonNull View itemView) {
            super(itemView);
            commentTextView = itemView.findViewById(R.id.comentarioTextView);
            dateTextView = itemView.findViewById(R.id.fechaTextView);
            libroTextView = itemView.findViewById(R.id.libroTextView);
            usuarioTextView = itemView.findViewById(R.id.usuarioTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Inicializar el botón de eliminar
        }
    }
}
