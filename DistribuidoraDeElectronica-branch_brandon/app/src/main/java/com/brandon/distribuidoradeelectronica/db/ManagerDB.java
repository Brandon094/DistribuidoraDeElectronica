package com.brandon.distribuidoradeelectronica.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.model.Producto;
import com.brandon.distribuidoradeelectronica.model.Usuario;
import com.brandon.distribuidoradeelectronica.model.Venta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerDB {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public ManagerDB(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public boolean openDatabase() {
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    public long insertDatos(String usuario, String contrasena) {
        if (!openDatabase()) return -1;

        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contrasena);

        long result = db.insert(Constantes.NOMBRE_TABLA, null, values);
        db.close();
        return result;
    }

    public long insertProductos(String nombre, String descripcion, String precio, String cantidad) {
        if (!openDatabase()) return -1;

        ContentValues values = new ContentValues();
        values.put(Constantes.PRODUCTOS, nombre);
        values.put(Constantes.DESCRIPCION_PRODUCTOS, descripcion);
        values.put(Constantes.CANTIDAD_PRODUCTOS, cantidad);
        values.put(Constantes.PRECIO, precio);

        long result = db.insert(Constantes.NOMBRE_TABLA_PRODUCTO, null, values);
        db.close();
        return result;
    }

    public long insertDatosAdmin(String usuario, String contrasena) {
        if (!openDatabase()) return -1;

        if (existeAdministrador()) {
            Toast.makeText(context, "Ya existe un administrador", Toast.LENGTH_LONG).show();
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contrasena);

        long result = db.insert(Constantes.NOMBRE_TABLA_ADMIN, null, values);
        db.close();
        return result;
    }

    public void borrarRegistro(String tabla, String whereClause, String[] whereArgs) {
        if (!openDatabase()) return;

        db.delete(tabla, whereClause, whereArgs);
        db.close();
    }

    public void borrarUsuario(Usuario usuario) {
        String whereClause = Constantes.NOMBRE + " = ?";
        String[] whereArgs = {usuario.getNombre()};
        borrarRegistro(Constantes.NOMBRE_TABLA, whereClause, whereArgs);
    }

    public boolean existeAdministrador() {
        if (!openDatabase()) return false;

        String[] columns = {Constantes.ID};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_ADMIN, columns, null,
                null, null, null, null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }

    public boolean validarCampos(String usuario, String contrasena) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(context, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public boolean validarUsuario(Usuario usuario) {
        if (!openDatabase()) return false;

        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario.getNombre(), usuario.getContraseña()};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA,
                columns, selection, selectionArgs,
                null, null, null);

        boolean resultado = cursor.moveToFirst();
        cursor.close();
        db.close();
        return resultado;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        if (!openDatabase()) return listaUsuarios;

        String[] columnas = {Constantes.ID, Constantes.NOMBRE, Constantes.CONTRASEÑA};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(Constantes.NOMBRE));
            @SuppressLint("Range") String contrasena = cursor.getString(cursor.getColumnIndex(Constantes.CONTRASEÑA));

            Usuario usuario = new Usuario(nombre, contrasena);
            listaUsuarios.add(usuario);
        }

        cursor.close();
        db.close();
        return listaUsuarios;
    }

    public void borrarProducto(Producto producto) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(producto.getId())};
        borrarRegistro(Constantes.NOMBRE_TABLA_PRODUCTO, whereClause, whereArgs);
    }

    public List<Producto> obtenerProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        if (!openDatabase()) return listaProductos;

        String[] columnas = {Constantes.ID, Constantes.PRODUCTOS, Constantes.DESCRIPCION_PRODUCTOS, Constantes.PRECIO, Constantes.IMAGEN_PRODUCTOS, Constantes.CANTIDAD_PRODUCTOS};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_PRODUCTO, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") String productoNombre = cursor.getString(cursor.getColumnIndex(Constantes.NOMBRE));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(Constantes.DESCRIPCION_PRODUCTOS));
            @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(Constantes.PRECIO));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.CANTIDAD_PRODUCTOS));
            @SuppressLint("Range") String imagen = cursor.getString(cursor.getColumnIndex(Constantes.IMAGEN_PRODUCTOS));

            Producto producto = new Producto(id, productoNombre, descripcion, cantidad, precio, imagen);
            listaProductos.add(producto);
        }

        cursor.close();
        db.close();
        return listaProductos;
    }

    public void borrarVenta(Venta venta) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(venta.getId())};
        borrarRegistro(Constantes.NOMBRE_TABLA_VENTA, whereClause, whereArgs);
    }

    public List<Venta> obtenerVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        if (!openDatabase()) return listaVentas;

        String[] columnas = {Constantes.ID, Constantes.VENTA_PRODUCTO_ID, Constantes.VENTA_CANTIDAD, Constantes.VENTA_FECHA};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_VENTA, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") int idProducto = cursor.getInt(cursor.getColumnIndex(Constantes.VENTA_PRODUCTO_ID));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.VENTA_CANTIDAD));
            @SuppressLint("Range") String fechaString = cursor.getString(cursor.getColumnIndex(Constantes.VENTA_FECHA));

            // Obtener el objeto Producto correspondiente al ID
            Producto producto = obtenerProductoPorId(idProducto);

            // Convertir la fecha de tipo String a tipo Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = null;
            try {
                fecha = dateFormat.parse(fechaString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Venta venta = new Venta(id, producto, cantidad, 0, fecha); // El valor de "precioTotal" se puede establecer a 0 o modificar según tu lógica
            listaVentas.add(venta);
        }

        cursor.close();
        db.close();
        return listaVentas;
    }

    private Producto obtenerProductoPorId(int idProducto) {
        if (!openDatabase()) return null;

        String[] columnas = {Constantes.ID, Constantes.NOMBRE, Constantes.DESCRIPCION_PRODUCTOS, Constantes.PRECIO, Constantes.IMAGEN_PRODUCTOS, Constantes.CANTIDAD_PRODUCTOS};
        String selection = Constantes.ID + " = ?";
        String[] selectionArgs = {String.valueOf(idProducto)};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_PRODUCTO, columnas, selection, selectionArgs, null, null, null);

        Producto producto = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(Constantes.NOMBRE));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(Constantes.DESCRIPCION_PRODUCTOS));
            @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(Constantes.PRECIO));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.CANTIDAD_PRODUCTOS));
            @SuppressLint("Range") String imagen = cursor.getString(cursor.getColumnIndex(Constantes.IMAGEN_PRODUCTOS));

            producto = new Producto(id, nombre, descripcion, cantidad, precio, imagen);
        }

        cursor.close();
        db.close();
        return producto;
    }
}
