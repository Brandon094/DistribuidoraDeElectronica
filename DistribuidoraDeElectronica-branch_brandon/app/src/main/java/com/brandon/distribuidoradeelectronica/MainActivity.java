package com.brandon.distribuidoradeelectronica;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

public class MainActivity extends AppCompatActivity {
    private Button botonRegistro;
    private Button botonIngresar;
    private EditText txtUsuario;
    private EditText txtContraseña;
    private DbHelper dbHelper;
    private ManagerDB managerDB;
    private Spinner spinnerUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear una instancia de DbHelper para administrar la base de datos
        dbHelper = new DbHelper(getApplicationContext());

        // Crear una instancia de ManagerDB para realizar operaciones en la base de datos
        managerDB = new ManagerDB(MainActivity.this);

        // Obtener una instancia de SQLiteDatabase para interactuar con la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insertar administrador inicial si no existe
        if (!managerDB.existeAdministrador()) {
            managerDB.insertDatosAdmin("admin", "admin123");
        }

        // Configurar el botón de registro para abrir la actividad de registro de usuarios
        botonRegistro = findViewById(R.id.btnRegistrarse);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        // Configurar el spinner para el tipo de usuario (Usuario o Administrador)
        spinnerUserType = findViewById(R.id.spinnerUserType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        // Configurar el botón de ingreso para validar el usuario y abrir la actividad correspondiente
        botonIngresar = findViewById(R.id.btnIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el tipo de usuario seleccionado en el spinner
                String userType = spinnerUserType.getSelectedItem().toString();

                // Obtener el usuario ingresado desde el campo de texto
                txtUsuario = findViewById(R.id.txtUsuario);
                String usuario = txtUsuario.getText().toString();

                // Obtener la contraseña ingresada desde el campo de texto
                txtContraseña = findViewById(R.id.txtContraseña);
                String contraseña = txtContraseña.getText().toString();

                // Crear una instancia de Usuario con los valores ingresados
                Usuario usuarioObjeto = new Usuario(usuario, contraseña);

                // Validar que los campos no estén vacíos
                if (managerDB.validarCampos(usuario, contraseña)) {
                    return; // Salir del método onClick sin continuar con la validación
                }

                // Verificar si la base de datos está abierta
                if (managerDB.openDatabase(db)) {
                    if (userType.equals("Usuario")) {
                        // Validar el usuario ingresado como usuario regular
                        boolean usuarioValidado = managerDB.validarUsuario(usuarioObjeto);
                        if (usuarioValidado) {
                            // El usuario es válido, iniciar la actividad del menú principal
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            startActivity(intent);
                        } else {
                            // El usuario no está registrado, abrir la actividad de registro de usuarios
                            Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                            startActivity(intent);
                        }
                    } else if (userType.equals("Administrador")) {
                        // Validar el usuario ingresado
                        boolean adminValidado = managerDB.existeAdministrador();
                        if (adminValidado) {
                            // El usuario es el administrador, abrir la actividad del menú de administrador
                            Intent intent = new Intent(MainActivity.this, MenuAdmin.class);
                            startActivity(intent);
                        } else {
                            // El usuario no es válido, abrir la actividad de registro de usuarios
                            Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}