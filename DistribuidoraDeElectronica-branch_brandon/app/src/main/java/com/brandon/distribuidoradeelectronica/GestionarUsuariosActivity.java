package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

import java.util.List;

public class GestionarUsuariosActivity extends AppCompatActivity {
    private List<Usuario> listaUsuarios;

    private Button btnAgregarUsuario;
    private DbHelper dbHelper;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuarios);

        // Obtener referencias a los elementos de la interfaz
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        // Crear instancia de DbHelper
        dbHelper = new DbHelper(this);
        // Crear instancia de ManagerDB
        managerDB = new ManagerDB(this);

        // Configurar listeners para los botones
        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para agregar un nuevo usuario
                // Aquí puedes abrir una nueva actividad o fragmento para agregar el usuario
                Intent intent = new Intent(GestionarUsuariosActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        // Mostrar los usuarios
        listaUsuarios = managerDB.obtenerUsuarios();
        ListViewUtils.mostrarDatosEnListView(GestionarUsuariosActivity.this, listaUsuarios, managerDB, R.id.listViewUsuarios);
    }
}
