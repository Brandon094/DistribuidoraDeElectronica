package com.brandon.distribuidoradeelectronica.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constantes.NOMBRE_DB, null, Constantes.VERSION_DB);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constantes.TablaUsuarios);
        sqLiteDatabase.execSQL(Constantes.TablaAdmin);
        sqLiteDatabase.execSQL(Constantes.TablaProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
