package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity {
    private Button botonGestionarUsuarios;
    private Button botonGestionarProductos;
    private Button botonGestionarVentas;
    private Button botonGenerarInforme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        // Configurar el botón de gestión de usuarios para abrir la actividad de gestión de usuarios
        botonGestionarUsuarios = findViewById(R.id.btnGestionarUsuarios);
        botonGestionarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad GestionarUsuariosActivity
                Intent intent = new Intent(MenuAdmin.this, GestionarUsuariosActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el botón de gestión de productos para abrir la actividad de gestión de productos
        botonGestionarProductos = findViewById(R.id.btnGestionarProductos);
        botonGestionarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad GestionarProductos
                Intent intent = new Intent(MenuAdmin.this, GestionarProductos.class);
                startActivity(intent);
            }
        });

        // Configurar el botón de gestión de ventas para abrir la actividad de gestión de ventas
        botonGestionarVentas = findViewById(R.id.btnGestionarVentas);
        botonGestionarVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad GestionarVentas
                Intent intent = new Intent(MenuAdmin.this, GestionarVentas.class);
                startActivity(intent);
            }
        });

        // Configurar el botón para generar informe para abrir la actividad de generar informe
        botonGenerarInforme = findViewById(R.id.btnGenerarInforme);
        botonGenerarInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad GenerarInforme
                Intent intent = new Intent(MenuAdmin.this, GenerarInforme.class);
                startActivity(intent);
            }
        });
    }
}