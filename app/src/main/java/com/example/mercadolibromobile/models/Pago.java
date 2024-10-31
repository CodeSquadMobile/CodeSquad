package com.example.mercadolibromobile.models;

public class Pago {
    private String numero_tarjeta;
    private String cvv;
    private String vencimiento;
    private String tipo_tarjeta;

    // Constructor sin el campo usuario
    public Pago(String numero_tarjeta, String cvv, String vencimiento, String tipo_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
        this.cvv = cvv;
        this.vencimiento = vencimiento;
        this.tipo_tarjeta = tipo_tarjeta;
    }

    public String getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getTipo_tarjeta() {
        return tipo_tarjeta;
    }

    public void setTipo_tarjeta(String tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }
}
