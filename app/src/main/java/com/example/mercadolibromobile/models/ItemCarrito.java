package com.example.mercadolibromobile.models;

public class ItemCarrito {
    private int libro;
    private int cantidad;

    // Constructor
    public ItemCarrito(int libro, int cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}