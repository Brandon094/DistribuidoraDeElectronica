package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {
    private Button btnVerProductos;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Configurar el botón "Ver Productos" para abrir la actividad VerProductos
        btnVerProductos = findViewById(R.id.btnVerProductos);
        btnVerProductos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, VerProdutos.class);
                startActivity(intent);
            }
        });

        // Configurar el botón "Comprar" para abrir la actividad Comprar
        btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Comprar.class);
                startActivity(intent);
            }
        });
    }
}
