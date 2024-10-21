package com.example.mercadolibromobile.models;

import com.google.gson.annotations.SerializedName;

public class ItemCarrito {
    @SerializedName("id_libro")  // Mapeo del campo 'id_libro'
    private int id_libro;  // ID del libro
    @SerializedName("cantidad")  // Mapeo del campo 'cantidad'
    private int cantidad;
    @SerializedName("precio")  // Mapeo del campo 'precio'
    private double precio; // Nuevo campo para almacenar el precio del libro

    public ItemCarrito(int id_libro, int cantidad, double precio) {  // Modificamos el constructor
        this.id_libro = id_libro;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y setters
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
