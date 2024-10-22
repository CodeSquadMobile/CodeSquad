package com.example.mercadolibromobile.models;

import com.google.gson.annotations.SerializedName;

public class ItemCarrito {
    @SerializedName("id_libro")
    private int id_libro;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("precio")
    private double precio;

    // Constructor
    public ItemCarrito(int id_libro, int cantidad, double precio) {
        this.id_libro = id_libro;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y Setters
    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
