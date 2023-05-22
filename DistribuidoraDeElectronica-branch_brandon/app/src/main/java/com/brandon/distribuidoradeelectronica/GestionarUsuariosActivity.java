package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestionarUsuariosActivity extends AppCompatActivity {
    private List<Usuario> listaUsuarios;
    private TextView textView13;
    private Button btnAgregarUsuario;
    private Button btnBorrarUsuario;
    private Button btnActualizarUsuario;
    private DbHelper dbHelper;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuarios);

        // Obtener referencias a los elementos de la interfaz
        textView13 = findViewById(R.id.textView13);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        btnBorrarUsuario = findViewById(R.id.btnBorrarUsuario);
        btnActualizarUsuario = findViewById(R.id.btnActualizarUsuario);

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
            }
        });

        btnBorrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para borrar el usuario seleccionado
                // Aquí puedes obtener el usuario seleccionado y realizar la acción correspondiente
            }
        });

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para actualizar los datos del usuario seleccionado
                // Aquí puedes obtener el usuario seleccionado y abrir una nueva actividad o fragmento para editar sus datos
            }
        });
        //Obtener usuarios de la base de datos
        listaUsuarios = managerDB.obtenerUsuarios();

        // Mostrar los usuarios
        mostrarUsuariosEnListView(listaUsuarios);
    }

    /**
     * Muestra los usuarios en un ListView.
     *
     * @param listaUsuarios La lista de usuarios a mostrar.
     */
    private void mostrarUsuariosEnListView(List<Usuario> listaUsuarios) {
        // Obtener referencia al ListView en la interfaz
        ListView listViewUsuarios = findViewById(R.id.listViewUsuarios);

        // Crear un ArrayList de Strings para almacenar los datos de los usuarios
        ArrayList<String> datosUsuarios = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            String nombreContraseña = usuario.getNombre() + " - " + usuario.getContraseña();
            datosUsuarios.add(nombreContraseña);
        }

        // Crear un ArrayAdapter para el ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosUsuarios);

        // Asignar el ArrayAdapter al ListView
        listViewUsuarios.setAdapter(arrayAdapter);

        // Configurar el listener para el ListView
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el usuario seleccionado
                Usuario usuarioSeleccionado = listaUsuarios.get(position);

                // Mostrar un diálogo o menú contextual para que el usuario seleccione la acción
                AlertDialog.Builder builder = new AlertDialog.Builder(GestionarUsuariosActivity.this);
                builder.setTitle("Acciones")
                        .setItems(new CharSequence[]{"Actualizar", "Borrar"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // Acción para actualizar el usuario seleccionado
                                    // Puedes abrir una nueva actividad o fragmento para editar los datos del usuario
                                    // Pasar el usuario seleccionado como argumento a la actividad o fragmento
                                    Intent intent = new Intent(GestionarUsuariosActivity.this, EditarUsuario.class);
                                    intent.putExtra("usuario", usuarioSeleccionado);
                                    startActivity(intent);
                                } else if (which == 1) {
                                    // Acción para borrar el usuario seleccionado
                                    // Puedes mostrar un diálogo de confirmación antes de borrar el usuario
                                    AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(GestionarUsuariosActivity.this);
                                    confirmDialogBuilder.setTitle("Confirmar borrado")
                                            .setMessage("¿Estás seguro de que deseas borrar este usuario?")
                                            .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Lógica para borrar el usuario seleccionado
                                                    // Puedes llamar al método correspondiente en tu clase de gestión de la base de datos
                                                    managerDB.borrarUsuario(usuarioSeleccionado);

                                                    // Actualizar la lista de usuarios y refrescar el ListView
                                                    final List<Usuario> finalListaUsuarios = managerDB.obtenerUsuarios();
                                                    mostrarUsuariosEnListView(finalListaUsuarios);
                                                }
                                            })
                                            .setNegativeButton("Cancelar", null)
                                            .show();
                                }
                            }
                        })
                        .show();
            }
        });
    }
}
