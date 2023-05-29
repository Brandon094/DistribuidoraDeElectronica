package com.brandon.distribuidoradeelectronica.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.db.DbHelper;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.listViewUtils.ListViewUtils;
import com.brandon.distribuidoradeelectronica.model.Venta;
import com.brandon.distribuidoradeelectronica.registros.RegistroUsuarios;
import com.brandon.distribuidoradeelectronica.registros.RegistroVentas;

import java.util.List;

public class GestionarVentas extends AppCompatActivity {
    private Button btnAgregarVenta;
    private DbHelper dbHelper;
    private ManagerDB managerDB;
    private List<Venta> listaVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ventas);

        // Obtener referencias a los elementos de la interfaz
        btnAgregarVenta = findViewById(R.id.btnAgregarVenta);

        // Crear instancia de DbHelper
        dbHelper = new DbHelper(this);
        // Crear instancia de ManagerDB
        managerDB = new ManagerDB(this);

        // Configurar listeners para los botones
        btnAgregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de registro de ventas al hacer clic en el botón "Agregar Venta"
                Intent intent = new Intent(GestionarVentas.this, RegistroVentas.class);
                startActivity(intent);
            }
        });

        // Mostrar las ventas en una lista
        listaVentas = managerDB.obtenerVentas();
        ListViewUtils.mostrarDatosEnListView(GestionarVentas.this, listaVentas, managerDB,
                R.id.listViewUsuarios);
    }
}
