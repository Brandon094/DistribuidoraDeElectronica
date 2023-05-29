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
import com.brandon.distribuidoradeelectronica.gestion.GestionarProductos;
import com.brandon.distribuidoradeelectronica.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class RegistroProductos extends AppCompatActivity {
    private EditText editTextNombre, editTextDescripcion, editTextPrecio, editTextCantidad;
    private Button buttonGuardar;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_productos);

        // Obtener una instancia de ManagerDB
        managerDB  = new ManagerDB(getApplicationContext());

        // Obtener referencias a los elementos de la interfaz de usuario
        editTextNombre = findViewById(R.id.txtnombreProducto);
        editTextDescripcion = findViewById(R.id.txtDescripcionProducto);
        editTextPrecio = findViewById(R.id.txtPrecioProducto);
        editTextCantidad = findViewById(R.id.txtCantidadProducto);

        // Configurar el botón "Guardar" para guardar el producto en la base de datos
        buttonGuardar = findViewById(R.id.btnAgregar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos de texto
                String nombreProducto = editTextNombre.getText().toString();
                String descripcionProducto = editTextDescripcion.getText().toString();
                String precioStr = editTextPrecio.getText().toString();
                String cantidadStr = editTextCantidad.getText().toString();

                // Validar que los campos obligatorios no estén vacíos
                List<Object> campos = new ArrayList<>();
                campos.add(nombreProducto);
                campos.add(descripcionProducto);
                boolean camposValidos = managerDB.validarCampos(campos.toArray());
                if (camposValidos) {
                    Toast.makeText(RegistroProductos.this, "Por favor complete todos los campos",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Validar que los campos de precio y cantidad no estén vacíos y contengan valores numéricos
                if (precioStr.isEmpty() || cantidadStr.isEmpty()) {
                    Toast.makeText(RegistroProductos.this, "Por favor complete los campos (precio, cantidad)",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(precioStr) || !TextUtils.isDigitsOnly(cantidadStr)) {
                    Toast.makeText(RegistroProductos.this, "Por favor ingrese solo valores numéricos en los " +
                            "campos (precio, cantidad)", Toast.LENGTH_LONG).show();
                    return;
                }

                // Convertir los valores de precio y cantidad a tipos numéricos
                double precioProducto = Double.parseDouble(precioStr);
                int cantidadProducto = Integer.parseInt(cantidadStr);

                // Crear una instancia de Producto con los valores ingresados
                Producto producto = new Producto(nombreProducto, descripcionProducto, precioProducto,
                        cantidadProducto);

                // Insertar el producto en la base de datos
                long resultado = managerDB.insertProductos(producto.getNombre(),
                        producto.getDescripcion(),
                        String.valueOf(producto.getPrecio()),
                        String.valueOf(producto.getCantidad()));

                if (resultado != -1) {
                    // Inserción exitosa, mostrar mensaje
                    Toast.makeText(RegistroProductos.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigir a la actividad de gestión de productos
                    Intent intent = new Intent(RegistroProductos.this, GestionarProductos.class);
                    startActivity(intent);
                    LimpiarCajasText();
                } else {
                    // Ocurrió un error durante la inserción, mostrar mensaje de error
                    Toast.makeText(RegistroProductos.this, "Error al registrar producto",
                            Toast.LENGTH_SHORT).show();
                    LimpiarCajasText();
                }
            }
        });
    }
    // Método para limpiar los campos de texto después de realizar el registro
    private void LimpiarCajasText(){
        editTextNombre.setText("");
        editTextDescripcion.setText("");
        editTextPrecio.setText("");
        editTextCantidad.setText("");
    }
}
