package com.brandon.distribuidoradeelectronica;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;

public class MainActivity extends AppCompatActivity {
    private Button botonRegistro;
    private Button botonIngresar;
    private EditText txtUsuario;
    private EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(getApplicationContext());
        ManagerDB managerDB = new ManagerDB(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ABRIR SECCION DE REGISTRO DE USUARIOS
        botonRegistro = findViewById(R.id.btnRegistrarse);
        botonRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,
                        RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        // ABRIR SECCION DE MENU PRINCIPAL
        botonIngresar = findViewById(R.id.btnIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OBTENER USUARIO INGRESADO
                txtUsuario = findViewById(R.id.txtUsuario);
                String usuario = txtUsuario.getText().toString();

                // OBTENER CONTRASEÑA INGRESADA
                txtContraseña = findViewById(R.id.txtContraseña);
                String contraseña = txtContraseña.getText().toString();

                // Validar campos
                if (managerDB.validarCampos(usuario, contraseña)) {
                    return; // Sale del método onClick sin continuar con la validación
                }

                if( managerDB.openDatabase(db) == true ){
                    boolean usuarioValidado = managerDB.validarUsuario(usuario, contraseña);
                    if (usuarioValidado == true) {
                        // El usuario es válido, inicia la nueva actividad
                        Intent intent = new Intent(MainActivity.this,
                                MenuPrincipal.class);
                        startActivity(intent);
                    } else {
                        boolean usuarioAdmin = managerDB.validarAdimin(usuario, contraseña);
                        if (usuarioAdmin == true) {
                            Intent intent = new Intent(MainActivity.this,
                                    MenuAdmin.class);
                        }else {
                            Intent intent = new Intent(MainActivity.this,
                                    RegistroUsuarios.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}