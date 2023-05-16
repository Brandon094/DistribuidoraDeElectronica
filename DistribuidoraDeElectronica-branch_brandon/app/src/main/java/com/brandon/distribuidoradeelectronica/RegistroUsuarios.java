package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;

public class RegistroUsuarios extends AppCompatActivity {

    private Button botonConfiRegistro;
    private EditText txtUsuario;
    private EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        // CONFIRMAR REGISTRO DE USUARIOS
        botonConfiRegistro = findViewById(R.id.btnConRegistro);
        botonConfiRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // OBTENER USUARIO INGRESADO
                txtUsuario = findViewById(R.id.txtUsuario);
                String usuario = txtUsuario.getText().toString();

                // OBTENER CONTRASEÑA INGRESADA
                txtContraseña = findViewById(R.id.txtContraseña);
                String contraseña = txtContraseña.getText().toString();

                // Crear instancia de DbHelper y SQLiteDatabase
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ManagerDB managerDB = new ManagerDB(RegistroUsuarios.this);
                managerDB.insertDatos(usuario, contraseña);
                Intent intent = new Intent(RegistroUsuarios.this,
                        MenuPrincipal.class);
                startActivity(intent);
            }
        });
    }
}
