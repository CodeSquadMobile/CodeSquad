// ItemCarrito.java
package com.example.mercadolibromobile.models;

public class ItemCarrito {
    private int libroId;
    private int cantidad;

    public ItemCarrito(int libroId, int cantidad) {
        this.libroId = libroId;
        this.cantidad = cantidad;
    }

    public int getLibroId() {
        return libroId;
    }

    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
