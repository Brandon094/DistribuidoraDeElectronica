package com.brandon.distribuidoradeelectronica.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brandon.distribuidoradeelectronica.compra.Comprar;
import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.verProductos.VerProdutos;

public class MenuPrincipal extends AppCompatActivity {
    private Button btnVerProductos;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Configurar el botón "Ver Productos" para abrir la actividad VerProdutos
        btnVerProductos = findViewById(R.id.btnVerProductos);
        btnVerProductos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad VerProdutos
                Intent intent = new Intent(MenuPrincipal.this, VerProdutos.class);
                startActivity(intent);
            }
        });

        // Configurar el botón "Comprar" para abrir la actividad Comprar
        btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de Intent para iniciar la actividad Comprar
                Intent intent = new Intent(MenuPrincipal.this, Comprar.class);
                startActivity(intent);
            }
        });
    }
}
