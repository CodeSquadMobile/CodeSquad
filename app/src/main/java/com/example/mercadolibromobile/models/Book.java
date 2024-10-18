package com.example.mercadolibromobile.models;

public class Book {
    private String titulo;
    private String autor;
    private String categoria;
    private double precio;
    private int stock;
    private String portada;
    private String descripcion; // Nuevo campo para la descripción del libro

    // Constructor
    public Book(String titulo, String autor, String categoria, double precio, int stock, String portada, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.portada = portada;
        this.descripcion = descripcion; // Inicializar la descripción
    }

    // Getters y Setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getDescripcion() { // Nuevo getter para la descripción
        return descripcion;
    }

    public void setDescripcion(String descripcion) { // Nuevo setter para la descripción
        this.descripcion = descripcion;
    }
}
