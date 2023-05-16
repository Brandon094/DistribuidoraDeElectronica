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

    // INFO TABLA ADMINISTRADORES
    public static final String NOMBRE_TABLA_ADMIN = "t_administradores";

    // TABLA USUARIOS
    public static final String TablaUsuarios = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA + " ( "+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOMBRE + " TEXT NOT NULL," + CONTRASEÑA + " TEXT NOT NULL)";

    public static final String TablaProductos = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA_PRODUCTO + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PRODUCTOS + " TEXT NOT NULL," + DESCRIPCION_PRODUCTOS + " TEXT NOT NULL," +
            CANTIDAD_PRODUCTOS + " INTEGER NOT NULL)";

    public static final String TablaAdmin = "CREATE TABLE IF NOT EXISTS " +
            NOMBRE_TABLA_ADMIN + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOMBRE + " TEXT NOT NULL," + CONTRASEÑA + " TEXT NOT NULL)";

}
