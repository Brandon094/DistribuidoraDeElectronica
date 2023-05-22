package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

public class RegistroUsuarios extends AppCompatActivity {

    private Button botonConfiRegistro;
    private EditText txtUsuario;
    private EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        // Configurar el botón de confirmar registro
        botonConfiRegistro = findViewById(R.id.btnConRegistro);
        botonConfiRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Obtener el usuario ingresado desde el campo de texto
                txtUsuario = findViewById(R.id.txtUsuario);
                String usuario = txtUsuario.getText().toString();

                // Obtener la contraseña ingresada desde el campo de texto
                txtContraseña = findViewById(R.id.txtContraseña);
                String contraseña = txtContraseña.getText().toString();

                // Crear una instancia de Usuario con los valores ingresados
                Usuario usuarioObjeto = new Usuario(usuario, contraseña);

                // Crear una instancia de DbHelper y SQLiteDatabase
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ManagerDB managerDB = new ManagerDB(RegistroUsuarios.this);

                // Validar campos
                if (managerDB.validarCampos(usuario, contraseña)) {
                    return; // Salir del método onClick sin continuar con la inserción
                }

                // Insertar datos en la base de datos
                long resultado = managerDB.insertDatos(usuarioObjeto.getNombre(), usuarioObjeto.getContraseña());

                if (resultado != -1) {
                    // Inserción exitosa, mostrar mensaje
                    Toast.makeText(RegistroUsuarios.this, "Registro exitoso",
                            Toast.LENGTH_SHORT).show();

                    // Redirigir a la actividad del menú principal
                    Intent intent = new Intent(RegistroUsuarios.this, MenuPrincipal.class);
                    startActivity(intent);
                } else {
                    // Ocurrió un error durante la inserción, mostrar mensaje de error
                    Toast.makeText(RegistroUsuarios.this, "Error al registrar usuario",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
