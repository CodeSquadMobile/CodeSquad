package com.example.mercadolibromobile.models;

import com.google.gson.annotations.SerializedName;

public class ItemCarrito {
    @SerializedName("libro")
    private int libro;

    @SerializedName("usuario")
    private int usuario;

    @SerializedName("cantidad")
    private int cantidad;

    public ItemCarrito(int libro, int usuario, int cantidad) {
        this.libro = libro;
        this.usuario = usuario;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}