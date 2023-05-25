package com.brandon.distribuidoradeelectronica;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.model.Producto;
import java.util.List;

public class GestionarProductos extends AppCompatActivity {
    private List<Producto> listaProductos;
    private Button btnAgregarProducto;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_productos);

        // Obtener referencia al botón de agregar producto
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        // Crear instancia de ManagerDB
        managerDB = new ManagerDB(this);

        // Configurar listener para el botón de agregar producto
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para agregar un nuevo producto
                Intent intent = new Intent(GestionarProductos.this, RegistroProductos.class);
                startActivity(intent);
            }
        });

        // Obtener productos de la base de datos
        listaProductos = managerDB.obtenerProductos();

        // Mostrar los productos en un ListView
        ListViewUtils.mostrarDatosEnListView(this, listaProductos, managerDB, R.id.listViewProductos);

    }
}
