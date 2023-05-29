package com.brandon.distribuidoradeelectronica.model;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {
    private int id;
    private String nombreVenta;
    private Producto producto;
    private int cantidad;
    private double precioTotal;
    private Date fecha;
    private String fechas;

    public Venta(int id, Producto producto,
                 int cantidad, Double precioTotal,
                 Date fecha) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    public Venta( String nombreVenta,
                 int cantidad, Double precioTotal,
                 String fechas) {
        this.nombreVenta = nombreVenta;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fechas = fechas;
    }



    // Métodos Getter y Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreVenta() {
        return nombreVenta;
    }

    public void setNombreVenta(Producto producto) {
        this.nombreVenta = nombreVenta;
    }

    public int getCantidadVenta() {
        return cantidad;
    }

    public void setCantidadVenta(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotalVenta() {
        return precioTotal;
    }

    public void setPrecioTotalVenta(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaVenta() {
        return fecha;
    }

    public String getFechaVentas() {
        return fechas;
    }

    public void setFechaVenta(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return  "id:\t" + id +
                "\nproducto:\t" + producto +
                "\ncantidad:\t" + cantidad +
                "\nprecio Total:\t" + precioTotal +
                "\nfecha:\t" + fecha;
    }
}
