package com.brandon.distribuidoradeelectronica.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.model.Usuario;

import org.w3c.dom.Text;

public class EditarUsuario extends AppCompatActivity {
    private TextView txtViewUsuarioActual;
    private TextView txtViewContraseñaActual;
    private EditText txtUsuarioNuevo;
    private EditText txtContraseñaNueva;
    private Button btnConfirmarEdicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        txtViewUsuarioActual = findViewById(R.id.textUsuarioActual);
        txtViewContraseñaActual = findViewById(R.id.textContraseñaActual);
        txtUsuarioNuevo = findViewById(R.id.txtUsuario);
        txtContraseñaNueva = findViewById(R.id.txtContraseña);
        btnConfirmarEdicion = findViewById(R.id.btnConfirmarEdicion);

        Intent intent = getIntent();
        Usuario usuario = intent.getParcelableExtra("usuario");

        txtViewUsuarioActual.setText(usuario.getNombre());
    }
}
