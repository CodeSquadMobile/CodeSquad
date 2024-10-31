package com.example.mercadolibromobile.models;

import com.google.gson.annotations.SerializedName;

public class Resena {
    private int id; // ID de la reseña (si es necesario)

    @SerializedName("libro") // Asocia el campo JSON "libro" con este atributo
    private int libro; // ID del libro

    @SerializedName("email_usuario") // Asocia el campo JSON "email_usuario" con este atributo
    private String emailUsuario; // Correo del usuario

    private String comentario; // Comentario de la reseña
    private String fechaCreacion; // Fecha de creación de la reseña

    // Constructor
    public Resena(int libro, String emailUsuario, String comentario, String fechaCreacion) {
        this.libro = libro;
        this.emailUsuario = emailUsuario;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters
    public int getId() {
        return id; // Devuelve el ID de la reseña
    }

    public void setId(int id) {
        this.id = id; // Establece el ID de la reseña (útil al agregar o actualizar)
    }

    public int getLibro() {
        return libro; // Devuelve el ID del libro
    }

    public String getEmailUsuario() {
        return emailUsuario; // Devuelve el correo del usuario
    }

    public String getComentario() {
        return comentario; // Devuelve el comentario
    }

    public String getFechaCreacion() {
        return fechaCreacion; // Devuelve la fecha de creación
    }
}
