package com.brandon.distribuidoradeelectronica.registros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.gestion.GestionarUsuariosActivity;
import com.brandon.distribuidoradeelectronica.menus.MenuPrincipal;
import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RegistroUsuarios extends AppCompatActivity {

    private Button botonConfiRegistro;
    private EditText txtUsuario;
    private EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        // Obtener referencia al botón de confirmar registro
        botonConfiRegistro = findViewById(R.id.btnConRegistro);

        // Configurar el listener para el botón de confirmar registro
        botonConfiRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el usuario ingresado desde el campo de texto
                txtUsuario = findViewById(R.id.txtUsuario);
                String usuario = txtUsuario.getText().toString();

                // Obtener la contraseña ingresada desde el campo de texto
                txtContraseña = findViewById(R.id.txtContraseña);
                String contraseña = txtContraseña.getText().toString();

                // Crear una instancia de Usuario con los valores ingresados
                Usuario usuarioObjeto = new Usuario(usuario, contraseña);

                // Crear una instancia de ManagerDB
                ManagerDB managerDB = new ManagerDB(RegistroUsuarios.this);

                // Crear una lista de campos para validar si están vacíos
                List<String> campos = new ArrayList<>();
                campos.add(usuario);
                campos.add(contraseña);

                // Validar que los campos no estén vacíos
                boolean camposVacios = managerDB.validarCampos(campos.toArray());
                if (!camposVacios) {
                    // Insertar datos en la base de datos
                    if (managerDB.existUser(usuario, contraseña)) {
                        // El usuario ya existe, mostrar mensaje de usuario existente
                        Toast.makeText(RegistroUsuarios.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                    } else {
                        long resultado = managerDB.insertDatos(usuarioObjeto.getNombre(),
                                usuarioObjeto.getContraseña());
                        if (resultado != -1) {
                            // Inserción exitosa, mostrar mensaje
                            Toast.makeText(RegistroUsuarios.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            LimpiarCajasText();
                            Intent intent = new Intent(RegistroUsuarios.this, GestionarUsuariosActivity.class);
                            startActivity(intent);
                        } else {
                            // Ocurrió un error durante la inserción, mostrar mensaje de error
                            Toast.makeText(RegistroUsuarios.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                            LimpiarCajasText();
                        }
                    }
                } else {
                    // Mostrar mensaje de error si hay campos vacíos
                    Toast.makeText(RegistroUsuarios.this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Método para limpiar los campos de texto después de realizar el registro
    private void LimpiarCajasText(){
        txtUsuario.setText("");
        txtContraseña.setText("");
    }
}
