package com.example.mercadolibromobile.models;

public class ItemCarrito {
    private int id_libro;  // ID del libro
    private int cantidad;
    private double precio;  // Agregar precio

    // Constructor
    public ItemCarrito(int id_libro, int cantidad, double precio) {
        this.id_libro = id_libro;
        this.cantidad = cantidad;
        this.precio = precio;  // Asignar el precio al constructor
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
