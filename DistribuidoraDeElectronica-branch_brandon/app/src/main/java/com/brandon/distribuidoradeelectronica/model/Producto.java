package com.brandon.distribuidoradeelectronica.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private String descripción;
    private double precio;
    private String imagen;
    private int cantidad;

    public Producto() {
    }

    public Producto(int id, String nombre, String descripción, int cantidad, double precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripción = descripción;
        this.precio = precio;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCantidad(int cantidad){ this.cantidad = cantidad; }

    public int getCantidad(){ return cantidad;}

    @NonNull
    @Override
    public String toString() {
        return "ID: " + id +
                "\nNombre: " + nombre +
                "\nDescripción: " + descripción +
                "\nPrecio: " + precio +
                "\nImagen: " + imagen +
                "\nCantidad: " + cantidad;
    }

}
