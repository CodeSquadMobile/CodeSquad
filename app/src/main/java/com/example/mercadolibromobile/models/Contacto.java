package com.example.mercadolibromobile.models;

public class Contacto {

    private String nombre;
    private String email;
    private String asunto;
    private String consulta;

    // Constructor
    public Contacto(String nombre,
                    String email,
                    String asunto,
                    String consulta) {
        this.nombre = nombre;
        this.email = email;
        this.asunto = asunto;
        this.consulta = consulta;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }
}
