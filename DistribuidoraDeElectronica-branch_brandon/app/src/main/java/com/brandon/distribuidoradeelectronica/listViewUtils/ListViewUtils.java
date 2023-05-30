package com.brandon.distribuidoradeelectronica.listViewUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.edit.EditarProducto;
import com.brandon.distribuidoradeelectronica.edit.EditarUsuario;
import com.brandon.distribuidoradeelectronica.edit.EditarVenta;
import com.brandon.distribuidoradeelectronica.model.Producto;
import com.brandon.distribuidoradeelectronica.model.Usuario;
import com.brandon.distribuidoradeelectronica.model.Venta;

import java.util.ArrayList;
import java.util.List;

public class ListViewUtils {

    /**
     * Método para mostrar datos en un ListView y configurar el listener para las acciones de editar y eliminar.
     *
     * @param activity   La actividad actual.
     * @param listaDatos La lista de datos a mostrar en el ListView.
     * @param managerDB  El objeto ManagerDB para realizar operaciones en la base de datos.
     * @param listViewId El ID del ListView en la interfaz.
     */
    public static void mostrarDatosEnListView(Activity activity, List<?> listaDatos, ManagerDB managerDB, int listViewId) {
        // Obtener referencia al ListView en la interfaz
        ListView listView = activity.findViewById(listViewId);

        // Crear un ArrayList de Strings para almacenar los datos
        ArrayList<String> datos = new ArrayList<>();
        for (Object dato : listaDatos) {
            datos.add(dato.toString());
        }

        // Crear un ArrayAdapter para el ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, datos);

        // Asignar el ArrayAdapter al ListView
        listView.setAdapter(arrayAdapter);

        // Configurar el listener para el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el objeto seleccionado
                Object datoSeleccionado = listaDatos.get(position);

                // Mostrar un diálogo o menú contextual para que el usuario seleccione la acción
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Acciones")
                        .setItems(new CharSequence[]{"Editar", "Eliminar"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // Acción para editar el objeto seleccionado
                                    // Abrir una nueva actividad o fragmento para editar los datos del objeto
                                    // Pasar el objeto seleccionado como argumento a la actividad o fragmento
                                    if (datoSeleccionado instanceof Usuario) {
                                        Intent intent = new Intent(activity, EditarUsuario.class);
                                        intent.putExtra("usuario", (Usuario) datoSeleccionado);
                                        activity.startActivity(intent);
                                    } else if (datoSeleccionado instanceof Producto) {
                                        Intent intent = new Intent(activity, EditarProducto.class);
                                        intent.putExtra("producto", (Producto) datoSeleccionado);
                                        activity.startActivity(intent);
                                    } else if (datoSeleccionado instanceof Venta) {
                                        Intent intent = new Intent(activity, EditarVenta.class);
                                        intent.putExtra("venta", (Venta) datoSeleccionado);
                                        activity.startActivity(intent);
                                    }
                                } else if (which == 1) {
                                    // Acción para eliminar el objeto seleccionado
                                    // Mostrar un diálogo de confirmación antes de eliminar el objeto
                                    AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(activity);
                                    confirmDialogBuilder.setTitle("Confirmar eliminación")
                                            .setMessage("¿Estás seguro de que deseas eliminar este objeto?")
                                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Lógica para eliminar el objeto seleccionado
                                                    // Llamar al método correspondiente en la clase de gestión de la base de datos
                                                    if (datoSeleccionado instanceof Usuario) {
                                                        managerDB.borrarUsuario((Usuario) datoSeleccionado);
                                                        final List<Usuario> finalListaUsuarios = managerDB.obtenerUsuarios();
                                                        mostrarDatosEnListView(activity, finalListaUsuarios, managerDB, listViewId);
                                                    } else if (datoSeleccionado instanceof Producto) {
                                                        managerDB.borrarProducto((Producto) datoSeleccionado);
                                                        final List<Producto> finalListaProductos = managerDB.obtenerProductos();
                                                        mostrarDatosEnListView(activity, finalListaProductos, managerDB, listViewId);
                                                    } else if (datoSeleccionado instanceof Venta) {
                                                        managerDB.borrarVenta((Venta) datoSeleccionado);
                                                        final List<Venta> finalListaVentas = managerDB.obtenerVentas();
                                                        mostrarDatosEnListView(activity, finalListaVentas, managerDB, listViewId);
                                                    }
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
