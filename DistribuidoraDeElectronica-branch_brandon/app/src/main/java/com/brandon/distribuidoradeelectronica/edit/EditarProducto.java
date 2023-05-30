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
import com.brandon.distribuidoradeelectronica.model.Producto;

public class EditarProducto extends AppCompatActivity {
    private TextView textNombreActual;
    private TextView textPrecioActual;
    private TextView textCantidadActual;
    private Button btnConfirmarEdicion;
    private EditText txtNombre;
    private EditText txtPrecio;
    private EditText txtCantidad;
    ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        // Crear una instancia de ManagerDB para realizar operaciones en la base de datos
        managerDB = new ManagerDB(EditarProducto.this);

        // Obtener referencias a los elementos de la interfaz
        textNombreActual = findViewById(R.id.textNombreProductoActual);
        textPrecioActual = findViewById(R.id.textPrecioActual);
        textCantidadActual = findViewById(R.id.textCantidadActual);
        txtNombre = findViewById(R.id.txtNombreProducto);
        txtPrecio = findViewById(R.id.txtPrecioProducto);
        txtCantidad = findViewById(R.id.txtCantidadProducto);
        btnConfirmarEdicion = findViewById(R.id.btnConfirmarEdicionProducto);

        // Obtener el producto seleccionado de la actividad anterior
        Intent intent = getIntent();
        Producto producto = (Producto) intent.getSerializableExtra("producto");

        // Mostrar los datos actuales del producto en los TextView correspondientes
        textNombreActual.setText("El producto actual es: " + producto.getNombre());
        textPrecioActual.setText("El precio actual: " + producto.getPrecio());
        textCantidadActual.setText("La cantidad actual: " + producto.getCantidad());

        // Configurar el listener del botón de confirmar edición
        btnConfirmarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores del producto ingresados en los EditText
                String nuevoProducto = txtNombre.getText().toString();
                String nuevoPrecio = txtPrecio.getText().toString();
                String nuevoCantidad = txtCantidad.getText().toString();

                // Editar el producto en la base de datos y obtener el resultado
                boolean editado = managerDB.editarProducto(producto, nuevoProducto, nuevoPrecio, nuevoCantidad);

                // Mostrar mensaje de confirmación o error según el resultado de la edición
                if (editado) {
                    Toast.makeText(EditarProducto.this, "Producto editado correctamente",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarProducto.this, "Error al editar el producto",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
