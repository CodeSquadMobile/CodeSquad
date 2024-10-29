package com.example.mercadolibromobile.models;


public class Resena {
    private String titulo_libro;
    private String email_usuario;
    private String comentario;
    private String fecha_creacion;

    public Resena(String titulo_libro, String email_usuario, String comentario, String fecha_creacion) {
        this.titulo_libro = titulo_libro;
        this.email_usuario = email_usuario;
        this.comentario = comentario;
        this.fecha_creacion = fecha_creacion;
    }

    public String getTituloLibro() {
        return titulo_libro;
    }

    public String getEmailUsuario() {
        return email_usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public String getFechaCreacion() {
        return fecha_creacion;
    }
}