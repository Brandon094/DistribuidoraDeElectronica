package com.brandon.distribuidoradeelectronica.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    DbHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public ManagerDB(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    // Verificar si la base de datos está abierta
    public boolean openDatabase(SQLiteDatabase db) {
        if (db != null) {
            return true;
        } else {
            Toast.makeText(context, "Error en la base de datos", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // Abrir base de datos en modo escritura
    public void openDbWr() {
        db = dbHelper.getWritableDatabase();
    }

    // Abrir base de datos en modo lectura
    public void openDbRd() {
        db = dbHelper.getReadableDatabase();
    }

    // Insertar datos en la tabla usuarios de la base de datos
    public long insertDatos(String usuario, String contraseña) {
        openDbWr();
        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contraseña);

        return db.insert(Constantes.NOMBRE_TABLA, null, values);
    }

    // Insertar datos en la tabla productos de la base de datos
    public long insertProductos(String nombre, String descripcion, String precio, String cantidad) {
        openDbWr();
        ContentValues values = new ContentValues();
        values.put(Constantes.PRODUCTOS, nombre);
        values.put(Constantes.DESCRIPCION_PRODUCTOS, descripcion);
        values.put(Constantes.CANTIDAD_PRODUCTOS, cantidad);
        values.put(Constantes.PRECIO, precio);

        return db.insert(Constantes.NOMBRE_TABLA_PRODUCTO, null, values);
    }

    // Insertar datos en la tabla admin de la base de datos
    public long insertDatosAdmin(String usuario, String contraseña) {
        openDbWr();

        // Verificar si ya existe un administrador
        if (existeAdministrador()) {
            // Mostrar un mensaje o realizar alguna acción apropiada
            Toast.makeText(context, "Ya existe un administrador", Toast.LENGTH_LONG).show();
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contraseña);

        return db.insert(Constantes.NOMBRE_TABLA_ADMIN, null, values);
    }

    // Borrar un usuario de la base de datos
    public void borrarUsuario(Usuario usuario) {
        openDbWr();

        // Define la cláusula WHERE para identificar al usuario a borrar
        String whereClause = Constantes.NOMBRE + " = ?";
        String[] whereArgs = {usuario.getNombre()};

        // Elimina el usuario de la tabla usuarios
        db.delete(Constantes.NOMBRE_TABLA, whereClause, whereArgs);

        db.close();
    }

    // Verificar si ya existe un administrador en la tabla admin
    public boolean existeAdministrador() {
        openDbRd();
        String[] columns = {Constantes.ID};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_ADMIN, columns, null,
                null, null, null, null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    // Validar campos de usuario y contraseña
    public boolean validarCampos(String usuario, String contraseña) {
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            // Si alguno de los campos está en blanco, muestra un mensaje
            Toast.makeText(context, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    // Validar existencia de usuarios en la base de datos
    public boolean validarUsuario(Usuario usuario) {
        openDbRd();
        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario.getNombre(), usuario.getContraseña()};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA,
                columns, selection, selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            // El usuario existe
            return true;
        } else {
            // El usuario no existe, muestra un mensaje
            Toast.makeText(context, "El usuario no está registrado", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // Obtener lista de usuarios desde la base de datos
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        openDbRd();

        String[] columnas = {Constantes.ID, Constantes.NOMBRE, Constantes.CONTRASEÑA};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(Constantes.NOMBRE));
            @SuppressLint("Range") String contraseña = cursor.getString(cursor.getColumnIndex(Constantes.CONTRASEÑA));

            Usuario usuario = new Usuario(nombre, contraseña);
            listaUsuarios.add(usuario);
        }

        cursor.close();
        db.close();

        return listaUsuarios;
    }
}
