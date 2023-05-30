package com.brandon.distribuidoradeelectronica.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constantes.NOMBRE_DB, null, Constantes.VERSION_DB);
        // Constructor de la clase DbHelper que recibe el contexto,
        // nombre de la base de datos y versión de la base de datos
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Método llamado cuando se crea la base de datos por primera vez
        // Se ejecutan las sentencias SQL para crear las tablas
        sqLiteDatabase.execSQL(Constantes.TablaUsuarios);
        sqLiteDatabase.execSQL(Constantes.TablaAdmin);
        sqLiteDatabase.execSQL(Constantes.TablaProductos);
        sqLiteDatabase.execSQL(Constantes.TablaVentas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Método llamado cuando se actualiza la versión de la base de datos
        // Aquí puedes realizar operaciones como modificar la estructura de la base de datos
        // Si no necesitas realizar ninguna operación, puedes dejar este método vacío
        // Verifica la versión antigua y la nueva para determinar qué operaciones realizar
    }
}
