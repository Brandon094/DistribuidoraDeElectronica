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
import com.brandon.distribuidoradeelectronica.model.Usuario;
import com.brandon.distribuidoradeelectronica.model.Venta;

public class EditarVenta extends AppCompatActivity {
    private TextView textNombreVentaActual;
    private TextView textPrecioVentaActual;
    private TextView textCantidadVentaActual;
    private EditText txtNombreVenta;
    private EditText txtPrecioVentaActual;
    private EditText txtCantidadProductoVenta;
    private Button btnConfirmarEdicion;

    ManagerDB managerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_venta);

        // Crear una instancia de ManagerDB para realizar operaciones en la base de datos
        managerDB = new ManagerDB(EditarVenta.this);

        // Obtener referencias a los elementos de la interfaz
        textNombreVentaActual = findViewById(R.id.textNombreVentaActual);
        textPrecioVentaActual = findViewById(R.id.textPrecioVentaActual);
        textCantidadVentaActual = findViewById(R.id.textCantidadVentaActual);
        txtNombreVenta = findViewById(R.id.txtNombreVenta);
        txtPrecioVentaActual = findViewById(R.id.txtPrecioProductoVenta);
        txtCantidadProductoVenta = findViewById(R.id.txtCantidadProductoVenta);

        // Obtener el usuario seleccionado de la actividad anterior
        Intent intent = getIntent();
       Venta venta = (Venta) intent.getSerializableExtra("producto");

        // Mostrar el nombre y la contraseña actuales del usuario en los TextView correspondientes
        textNombreVentaActual.setText("El producto actual es: " + venta.toString());
        textPrecioVentaActual.setText("El precio actual: " + venta.getPrecioTotalVenta());
        textCantidadVentaActual.setText("La cantidad actual: " + venta.getCantidadVenta());

        btnConfirmarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores del producto ingresados en los EditText
                String nuevoProducto = txtNombreVenta.getText().toString();
                String nuevoPrecio = txtPrecioVentaActual.getText().toString();
                String nuevoCantidad = txtCantidadProductoVenta.getText().toString();

                // Editar el producto en la base de datos y obtener el resultado
                boolean editado = managerDB.editarVenta(venta, nuevoProducto, nuevoPrecio,
                nuevoCantidad);

                // Mostrar mensaje de confirmación o error según el resultado de la edición
                if (editado) {
                    Toast.makeText(EditarVenta.this, "Venta editado correctamente",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarVenta.this, "Error al editar la venta",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}