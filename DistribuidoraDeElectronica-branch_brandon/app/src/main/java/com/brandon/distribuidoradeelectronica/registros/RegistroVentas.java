package com.brandon.distribuidoradeelectronica.registros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.db.ManagerDB;
import com.brandon.distribuidoradeelectronica.gestion.GestionarVentas;
import com.brandon.distribuidoradeelectronica.model.Venta;

import java.util.ArrayList;
import java.util.List;

public class RegistroVentas extends AppCompatActivity {
    private EditText editTextNombreVenta, editTextFechaVenta, editTextPrecioVenta,
            editTextCantidadVenta;
    private Button buttonGuardar;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ventas);

        // Obtener una instancia de ManagerDB
        managerDB  = new ManagerDB(getApplicationContext());

        // Obtener referencias a los elementos de la interfaz de usuario
        editTextNombreVenta = findViewById(R.id.txtnombreVenta);
        editTextFechaVenta = findViewById(R.id.txtfechaVenta);
        editTextPrecioVenta = findViewById(R.id.txtPrecioTotalVenta);
        editTextCantidadVenta = findViewById(R.id.txtCantidadVenta);

        // Configurar el botón "Guardar" para guardar la venta en la base de datos
        buttonGuardar = findViewById(R.id.btnAgregar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos de texto
                String nombreVenta = editTextNombreVenta.getText().toString();
                String cantidadVenta = editTextCantidadVenta.getText().toString();
                String precioTotalVenta = editTextPrecioVenta.getText().toString();
                String fechaVenta = editTextFechaVenta.getText().toString();

                // Validar que los campos obligatorios no estén vacíos
                List<Object> campos = new ArrayList<>();
                campos.add(nombreVenta);
                //campos.add(fechaVenta);

                boolean camposValidos = managerDB.validarCampos(campos.toArray());

                if (!camposValidos) {
                    Toast.makeText(RegistroVentas.this, "Por favor complete todos los campos",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Validar que los campos de precio y cantidad no estén vacíos y contengan valores numéricos
                if (precioTotalVenta.isEmpty() || cantidadVenta.isEmpty()) {
                    Toast.makeText(RegistroVentas.this, "Por favor complete los campos (precio, " +
                            "cantidad)", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(precioTotalVenta) || !TextUtils.isDigitsOnly(cantidadVenta)) {
                    Toast.makeText(RegistroVentas.this, "Por favor ingrese solo valores numéricos en " +
                            "los campos (precio, cantidad)", Toast.LENGTH_LONG).show();
                    return;
                }

                // Convertir los valores de precio y cantidad a tipos numéricos
                double precioVenta = Double.parseDouble(precioTotalVenta);
                int c_Ventas = Integer.parseInt(cantidadVenta);

                //LocalDate fechaVenta = LocalDate.now();

                // Crear una instancia de Venta con los valores ingresados
                Venta venta = new Venta(nombreVenta, c_Ventas, precioVenta,
                        fechaVenta);

                // Insertar la venta en la base de datos
                long resultado = managerDB.insertVentas(venta.getNombreVenta(),
                        String.valueOf(venta.getCantidadVenta()),
                        String.valueOf(venta.getPrecioTotalVenta()),
                        String.valueOf(venta.getFechaVentas()));

                if (resultado != -1) {
                    // Inserción exitosa, mostrar mensaje
                    Toast.makeText(RegistroVentas.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigir a la actividad de gestión de ventas
                    Intent intent = new Intent(RegistroVentas.this, GestionarVentas.class);
                    startActivity(intent);
                    LimpiarCajasText();
                } else {
                    // Ocurrió un error durante la inserción, mostrar mensaje de error
                    Toast.makeText(RegistroVentas.this, "Error al registrar venta",
                            Toast.LENGTH_SHORT).show();
                    LimpiarCajasText();
                }
            }
        });
    }

    // Método para limpiar los campos de texto después de realizar el registro
    private void LimpiarCajasText(){
        editTextNombreVenta.setText("");
        editTextFechaVenta.setText("");
        editTextPrecioVenta.setText("");
        editTextCantidadVenta.setText("");
    }
}

