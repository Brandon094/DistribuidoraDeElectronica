package com.brandon.distribuidoradeelectronica.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private String contraseña;

    public Usuario(String id, String nombre, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public Usuario( String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    // Métodos Getter y Setter

    public String getNombre() {

        return nombre;
    }

    public String setNombre(String nombre) {

        this.nombre = nombre;
        return nombre;
    }

    public String getContraseña() {

        return contraseña;
    }

    public String setContraseña(String contraseña) {

        this.contraseña = contraseña;
        return contraseña;
    }
    // Métodos Getter y Setter para "id"
    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    @Override
    public String toString() {
        return "id: " + "\t" + id +
                "\nUsuario: " + "\t" + nombre +
                "\nContraseña: " + "\t" + contraseña;
    }
}
