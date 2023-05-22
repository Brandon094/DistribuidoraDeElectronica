package com.brandon.distribuidoradeelectronica.model;

public class Administrador {
    private String nombre;
    private String contraseña;
    private int nivelAcceso;

    public Administrador(String nombre, String contraseña, int nivelAcceso) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.nivelAcceso = nivelAcceso;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}
