package com.brandon.distribuidoradeelectronica.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class ManagerDB {
    DbHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public ManagerDB(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void oPenDbWr(){
        db = dbHelper.getWritableDatabase();
    }

    public void oPendDbRd(){
        db = dbHelper.getReadableDatabase();
    }

    public long insertDatos(String usuario, String contraseña) {
        oPenDbWr();
        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contraseña);

        return db.insert(Constantes.NOMBRE_TABLA,null,values);
    }

    public boolean validarCampos(String usuario, String contraseña) {
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            // Si alguno de los campos está en blanco, muestra un mensaje
            Toast.makeText(context, "Por favor complete todos los campos",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    // VERIFICAR QUE ESTE ABIERTA LA BASE DE DATOS
    public boolean openDatabase(SQLiteDatabase db){
        if (db != null){
            return true;
        } else {
            Toast.makeText(context, "Error en la base de datos",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    // VALIDAR EXISTENCIA USUARIOS
    public boolean validarUsuario(String usuario, String contraseña) {
        oPendDbRd();
        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario, contraseña};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA,
                columns, selection, selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            // El usuario existe
            return true;
        } else {
            // El usuario no existe
            Toast.makeText(context, "El usuario no está registrado",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // VALIDAR EXISTENCIA DEL ADMIN
    public boolean validarAdimin(String usuario, String contraseña){
        oPendDbRd();
        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario, contraseña};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA_ADMIN,
                columns, selection, selectionArgs,
                null, null, null);
        if (cursor.moveToFirst()) {
            // El usuario existe
            return true;
        } else {
            // El usuario no existe
            Toast.makeText(context, "El usuario administrador no está registrado",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
