package com.brandon.distribuidoradeelectronica.db;

public class Constantes {

    // INFO DE LA DB
    public static final String NOMBRE_DB = "distribuidora";
    public static final int VERSION_DB = 1;

    // INFO TABLA USUARIOS
    public static final String NOMBRE_TABLA = "t_usuarios";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String CONTRASEÑA = "contraseña";

    // INFO TABLA PRODUCTOS
    public static final String NOMBRE_TABLA_PRODUCTO = "t_productos";
    public static final String PRODUCTOS = "productos";
    public static final String DESCRIPCION_PRODUCTOS = "descripcion";
    public static final String CANTIDAD_PRODUCTOS = "cantidad";
    public static final String PRECIO = "precio";

    // INFO TABLA VENTAS
    public static final String NOMBRE_TABLA_VENTA = "t_ventas";
    public static final String VENTA_ID = "id";
    public static final String VENTA_PRODUCTO = "producto_id";
    public static final String VENTA_CANTIDAD = "cantidad";
    public static final String VENTA_PRECIO_TOTAL = "precio_total";
    public static final String VENTA_FECHA = "fecha";

    // INFO TABLA ADMINISTRADORES
    public static final String NOMBRE_TABLA_ADMIN = "t_administradores";
    public static final String ADMIN_ID = "id";
    public static final String ADMIN_NOMBRE = "nombre";
    public static final String ADMIN_CONTRASEÑA = "contraseña";

    // TABLA USUARIOS
    public static final String TablaUsuarios = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA + " ( "+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOMBRE + " TEXT NOT NULL," + CONTRASEÑA + " TEXT NOT NULL)";

    // TABLA PRODUCTOS
    public static final String TablaProductos = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA_PRODUCTO + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PRODUCTOS + " TEXT NOT NULL," + DESCRIPCION_PRODUCTOS + " TEXT NOT NULL," +
            CANTIDAD_PRODUCTOS + " INTEGER NOT NULL," + PRECIO + " REAL NOT NULL)";

    // TABLA VENTAS
    public static final String TablaVentas = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA_VENTA + " (" + VENTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VENTA_PRODUCTO + " INTEGER NOT NULL," + VENTA_CANTIDAD + " INTEGER NOT NULL," +
            VENTA_PRECIO_TOTAL + " REAL NOT NULL," + VENTA_FECHA + " TEXT NOT NULL)";

    // TABLA ADMINISTRADORES
    public static final String TablaAdmin = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA_ADMIN + " (" + ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ADMIN_NOMBRE + " TEXT NOT NULL," + ADMIN_CONTRASEÑA + " TEXT NOT NULL)";
}