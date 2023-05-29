package com.brandon.distribuidoradeelectronica.verProductos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.brandon.distribuidoradeelectronica.R;
import com.brandon.distribuidoradeelectronica.compra.Comprar;
import com.brandon.distribuidoradeelectronica.model.Producto;

public class VerProdutos extends AppCompatActivity implements View.OnClickListener {
    private ImageButton botonProducto01;
    private ImageButton botonProducto02;
    private ImageButton botonProducto03;
    private ImageButton botonProducto04;
    private ImageButton botonProducto05;
    private ImageButton botonProducto06;
    private ImageButton botonProducto07;
    private ImageButton botonProducto08;
    private ImageButton botonProducto09;
    private ImageButton botonProducto010;

    Producto producto = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_produtos);

        // Obtener referencias a los botones de imagen en el layout
        botonProducto01 = findViewById(R.id.btnProducto01);
        botonProducto02 = findViewById(R.id.btnProducto02);
        botonProducto03 = findViewById(R.id.btnProducto03);
        botonProducto04 = findViewById(R.id.btnProducto04);
        botonProducto05 = findViewById(R.id.btnProducto05);
        botonProducto06 = findViewById(R.id.btnProducto06);
        botonProducto07 = findViewById(R.id.btnProducto07);
        botonProducto08 = findViewById(R.id.btnProducto08);
        botonProducto09 = findViewById(R.id.btnProducto09);
        botonProducto010 = findViewById(R.id.btnProducto010);

        // Establecer el listener de clic para los botones de imagen
        botonProducto01.setOnClickListener(this);
        botonProducto02.setOnClickListener(this);
        botonProducto03.setOnClickListener(this);
        botonProducto04.setOnClickListener(this);
        botonProducto05.setOnClickListener(this);
        botonProducto06.setOnClickListener(this);
        botonProducto07.setOnClickListener(this);
        botonProducto08.setOnClickListener(this);
        botonProducto09.setOnClickListener(this);
        botonProducto010.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Verificar qué botón de imagen se ha clicado y realizar la acción correspondiente
        if (v == botonProducto01) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto02) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto03) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto04) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto05) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto06) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto07) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto08) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto09) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        } else if (v == botonProducto010) {
            onProductoClicked(producto.getNombre(), producto.getDescripcion(),
                              producto.getPrecio(), producto.getCantidad());
        }
    }
    private void onProductoClicked(String nombreProducto, String descripcionProducto, double precioProducto, int cantidadProducto) {
        // Acciones a realizar cuando se hace clic en un botón de imagen
        Intent intent = new Intent(VerProdutos.this, Comprar.class);
        intent.putExtra("nombreProducto", nombreProducto); // Pasa el nombre del producto como extra
        intent.putExtra("descripcionProducto", descripcionProducto); // Pasa la descripción del producto como extra
        intent.putExtra("precioProducto", precioProducto); // Pasa el precio del producto como extra
        intent.putExtra("cantidadProducto", cantidadProducto); // Pasa la cantidad de productos como extra
        startActivity(intent);
    }
}
