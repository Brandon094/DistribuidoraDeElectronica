package com.brandon.distribuidoradeelectronica.inicio;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.menus.MenuAdmin;
import com.brandon.distribuidoradeelectronica.menus.MenuPrincipal;
import com.brandon.distribuidoradeelectronica.model.Usuario;
import com.brandon.distribuidoradeelectronica.registros.RegistroUsuarios;
import com.brandon.distribuidoradeelectronica.registros.RegistroVentas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button botonRegistro;
    private Button botonIngresar;
    private EditText txtUsuario;
    private EditText txtContraseña;
    private ManagerDB managerDB;
    private Spinner spinnerUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del objeto para la gestión de la base de datos
        managerDB = new ManagerDB(MainActivity.this);
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Obtención de referencias a los elementos de la interfaz
        botonRegistro = findViewById(R.id.btnRegistrarse);
        botonIngresar = findViewById(R.id.btnIngresar);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        spinnerUserType = findViewById(R.id.spinnerUserType);

        // Configuración del adaptador y opciones del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        // Verificar si existe al menos un administrador en la base de datos
        if(!managerDB.existeAdministrador()){
            // Si no existe, insertar un administrador predeterminado
            managerDB.insertDatosAdmin("admin", "admin123");
        }

        // Configuración del botón "Registrarse"
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de registro de usuarios
                Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        // Configuración del botón "Ingresar"
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el tipo de usuario seleccionado en el spinner
                String userType = spinnerUserType.getSelectedItem().toString();

                // Obtener el nombre de usuario y contraseña ingresados
                String usuario = txtUsuario.getText().toString();
                String contraseña = txtContraseña.getText().toString();

                // Crear un objeto Usuario con los datos ingresados
                Usuario usuarioObjeto = new Usuario(usuario, contraseña);

                // Crear una lista de campos para validar si están vacíos
                List<Object> campos = new ArrayList<>();
                campos.add(usuario);
                campos.add(contraseña);

                // Validar si hay campos vacíos
                boolean camposVacios = managerDB.validarCampos(campos.toArray());

                if (!camposVacios) {
                    // Abrir la base de datos
                    if (managerDB.openDatabase()) {
                        if (userType.equals("Usuario")) {
                            // Validar el usuario ingresado
                            boolean usuarioValidado = managerDB.validarUsuario(usuarioObjeto);
                            if (usuarioValidado) {
                                LimpiarCajasText();
                                // Iniciar la actividad del menú principal para usuarios
                                Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                                startActivity(intent);
                            } else {
                                LimpiarCajasText();
                                // Si el usuario no existe, iniciar la actividad de registro de usuarios
                                Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                                startActivity(intent);
                            }
                        } else if (userType.equals("Administrador")) {
                            // Verificar si existe al menos un administrador en la base de datos
                            boolean adminValidado = managerDB.validarAdmin(usuarioObjeto);
                            if (adminValidado) {
                                LimpiarCajasText();
                                // Iniciar la actividad del menú principal para administradores
                                Intent intent = new Intent(MainActivity.this, MenuAdmin.class);
                                startActivity(intent);
                            } else {
                                LimpiarCajasText();
                                Toast.makeText(MainActivity.this, "Administrador incorrecto",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    // Mostrar mensaje de error si hay campos vacíos
                    Toast.makeText(MainActivity.this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
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
