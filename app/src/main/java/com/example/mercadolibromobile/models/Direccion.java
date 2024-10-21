package com.example.mercadolibromobile.models;

public class Direccion {
    private int id;
    private String calle;
    private int numero;
    private String ciudad;
    private String provincia;

    // Constructor para crear una dirección sin ID (para agregar una nueva)
    public Direccion(String calle, int numero, String ciudad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    // Constructor para obtener una dirección existente (con ID)
    public Direccion(int id, String calle, int numero, String ciudad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    // Getters y Setters para cada campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    // Método toString para mostrar la dirección en la lista de manera legible
    @Override
    public String toString() {
        return calle + " " + numero + ", " + ciudad + ", " + provincia;
    }
}
