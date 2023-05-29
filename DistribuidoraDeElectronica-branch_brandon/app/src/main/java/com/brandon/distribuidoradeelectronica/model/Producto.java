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
        // Constructor vacío necesario para Firebase
    }

    public Producto(int id, String nombre,
                    double precio, int cantidad,
                    String descripción) {
        this.id = id;
        this.nombre = nombre;
        this.descripción = descripción;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(String nombre, String descripción,
                    double precio, int cantidad) {
        this.nombre = nombre;
        this.descripción = descripción;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Métodos Getter y Setter

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

    public String getDescripcion() {
        return descripción;
    }

    public void setDescripcion(String descripción) {
        this.descripción = descripción;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: " + "\t" + id +
                "\nNombre: " + "\t" + nombre +
                "\nDescripción: " + "\t" + descripción +
                "\nPrecio: " + "\t" + precio +
                "\nCantidad: " + "\t" + cantidad;
    }
}
