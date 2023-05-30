package com.brandon.distribuidoradeelectronica.edit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

public class EditarUsuario extends AppCompatActivity {
    private TextView txtViewUsuarioActual;
    private TextView txtViewContraseñaActual;
    private EditText txtUsuarioNuevo;
    private EditText txtContraseñaNueva;
    private Button btnConfirmarEdicion;
    ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Crear una instancia de ManagerDB para realizar operaciones en la base de datos
        managerDB = new ManagerDB(EditarUsuario.this);

        // Obtener referencias a los elementos de la interfaz
        txtViewUsuarioActual = findViewById(R.id.textUsuarioActual);
        txtViewContraseñaActual = findViewById(R.id.textContraseñaActual);
        txtUsuarioNuevo = findViewById(R.id.txtUsuario);
        txtContraseñaNueva = findViewById(R.id.txtContraseña);
        btnConfirmarEdicion = findViewById(R.id.btnConfirmarEdicion);

        // Obtener el usuario seleccionado de la actividad anterior
        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");

        // Mostrar el nombre y la contraseña actuales del usuario en los TextView correspondientes
        txtViewUsuarioActual.setText("El nombre actual es: " + (usuario.getNombre() != null ? usuario.getNombre() : ""));
        txtViewContraseñaActual.setText("La contraseña actual es: " + (usuario.getContraseña() != null ? usuario.getContraseña() : ""));

        // Configurar el listener del botón de confirmar edición
        btnConfirmarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoUsuario = txtUsuarioNuevo.getText().toString();
                String nuevaContraseña = txtContraseñaNueva.getText().toString();

                // Editar el usuario en la base de datos y obtener el resultado
                boolean editado = managerDB.editarUsuario(usuario, nuevoUsuario, nuevaContraseña);

                // Mostrar mensaje de confirmación o error según el resultado de la edición
                if (editado) {
                    Toast.makeText(EditarUsuario.this, "Usuario editado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarUsuario.this, "Error al editar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
