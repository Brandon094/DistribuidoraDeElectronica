package com.brandon.distribuidoradeelectronica.model;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {
    private int id;
    private Producto producto;
    private int cantidad;
    private double precioTotal;
    private Date fecha;

    public Venta(int id, Producto producto, int cantidad, double precioTotal, Date fecha) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioTotal=" + precioTotal +
                ", fecha=" + fecha +
                '}';
    }
}