package com.brandon.distribuidoradeelectronica.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brandon.distribuidoradeelectronica.model.Producto;
import com.brandon.distribuidoradeelectronica.model.Usuario;
import com.brandon.distribuidoradeelectronica.model.Venta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerDB {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public ManagerDB(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    /**
     * Abre la base de datos en modo escritura.
     *
     * @return true si la base de datos se abrió correctamente, false de lo contrario.
     */
    public boolean openDatabase() {
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }
    /**
     * Abre la base de datos en modo lectura.
     *
     * @return true si la base de datos se abrió correctamente, false de lo contrario.
     */
    public boolean dataBaseRead() {
        db = dbHelper.getReadableDatabase();
        return (db != null);
    }
    /**
     * Inserta datos de usuario en la tabla de la base de datos.
     *
     * @param usuario    Nombre de usuario.
     * @param contrasena Contraseña del usuario.
     * @return El ID del nuevo registro insertado, -1 si la operación falla.
     */
    public long insertDatos(String usuario, String contrasena) {
        if (!openDatabase()) return -1;

        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contrasena);

        long result = db.insert(Constantes.NOMBRE_TABLA, null, values);
        db.close();
        return result;
    }
    /**
     * Inserta datos de productos en la tabla de la base de datos.
     *
     * @param nombre     Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio     Precio del producto.
     * @param cantidad   Cantidad del producto.
     * @return El ID del nuevo registro insertado, -1 si la operación falla.
     */
    public long insertProductos(String nombre, String descripcion, String precio, String cantidad) {
        if (!openDatabase()) return -1;

        ContentValues values = new ContentValues();
        values.put(Constantes.PRODUCTOS, nombre);
        values.put(Constantes.DESCRIPCION_PRODUCTOS, descripcion);
        values.put(Constantes.CANTIDAD_PRODUCTOS, cantidad);
        values.put(Constantes.PRECIO, precio);

        long result = db.insert(Constantes.NOMBRE_TABLA_PRODUCTO, null, values);
        db.close();
        return result;
    }
    /**
     * Inserta los datos de una venta en la tabla de la base de datos.
     *
     * @param nombre   Nombre del producto vendido.
     * @param cantidad Cantidad del producto vendido.
     * @param precio   Precio total de la venta.
     * @param fecha    Fecha de la venta.
     * @return El ID del nuevo registro insertado, -1 si la operación falla.
     */
    public long insertVentas(String nombre, String cantidad, String precio, String fecha) {
        if (!openDatabase()) return -1;

        ContentValues values = new ContentValues();
        values.put(Constantes.VENTA_PRODUCTO, nombre);
        values.put(Constantes.VENTA_CANTIDAD, cantidad);
        values.put(Constantes.VENTA_PRECIO_TOTAL, precio);
        values.put(Constantes.VENTA_FECHA, fecha);

        long result = db.insert(Constantes.NOMBRE_TABLA_VENTA, null, values);
        db.close();
        return result;
    }
    /**
     * Inserta los datos del administrador en la tabla de la base de datos.
     *
     * @param usuario     Nombre de usuario del administrador.
     * @param contraseña  Contraseña del administrador.
     * @return El ID del nuevo registro insertado, -1 si la operación falla o si ya existe un administrador registrado.
     */
    public long insertDatosAdmin(String usuario, String contraseña) {
        if (!openDatabase()) return -1;

        if (existeAdministrador()) {
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, usuario);
        values.put(Constantes.CONTRASEÑA, contraseña);

        long result = db.insert(Constantes.NOMBRE_TABLA_ADMIN, null, values);
        return result;
    }

    /**
     * Elimina un registro de una tabla específica en la base de datos.
     *
     * @param tabla       Nombre de la tabla.
     * @param whereClause Cláusula WHERE para especificar la condición de eliminación.
     * @param whereArgs   Argumentos de la cláusula WHERE.
     */
    public void borrarRegistro(String tabla, String whereClause, String[] whereArgs) {
        if (!openDatabase()) return;

        db.delete(tabla, whereClause, whereArgs);
        db.close();
    }

    /**
     * Elimina un registro de usuario de la tabla de usuarios en la base de datos.
     *
     * @param usuario Usuario a borrar.
     */
    public void borrarUsuario(Usuario usuario) {
        String whereClause = Constantes.NOMBRE + " = ?";
        String[] whereArgs = {usuario.getNombre()};
        borrarRegistro(Constantes.NOMBRE_TABLA, whereClause, whereArgs);
    }

    /**
     * Verifica si existe al menos un administrador registrado en la base de datos.
     *
     * @return true si existe al menos un administrador, false de lo contrario.
     */
    public boolean existeAdministrador() {
        if(!openDatabase())return false;

        String[] columns = {Constantes.ID};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_ADMIN, columns,
                null, null, null,null,null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }
    /**
     * Valida las credenciales de un administrador en la base de datos.
     *
     * @param usuario Objeto Usuario que contiene el nombre de usuario y la contraseña a validar.
     * @return true si las credenciales son válidas, false si no lo son o si ocurre algún error.
     */
    public boolean validarAdmin(Usuario usuario) {
        if (!openDatabase()) return false;

        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario.getNombre(), usuario.getContraseña()};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA_ADMIN,
                columns, selection, selectionArgs,
                null, null, null);

        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    /**
     * Valida los campos pasados como argumentos para asegurarse de que no estén vacíos.
     *
     * @param campos Campos a validar.
     * @return true si todos los campos están llenos, false de lo contrario.
     */
    public boolean validarCampos(Object... campos) {
        for (Object campo : campos) {
            String campoStr = (String) campo;
            if (campoStr.isEmpty()) {
                return true;
            }

        }
        return false;
    }
    /**
     * Valida las credenciales de un administrador en la base de datos.
     *
     * @param usuario Objeto Usuario que contiene el nombre de usuario y la contraseña a validar.
     * @return true si las credenciales son válidas, false si no lo son o si ocurre algún error.
     */
    public boolean existUser(String usuario, String contraseña) {
        dataBaseRead();

        String query = "SELECT * FROM " + Constantes.NOMBRE_TABLA +
                " WHERE " + Constantes.NOMBRE + " = '" + usuario + "'" +
                " AND " + Constantes.CONTRASEÑA + " = '" + contraseña + "'";

        Cursor cursor = db.rawQuery(query, null);
        boolean existe = cursor.moveToFirst();
        cursor.close();

        return existe;
    }

    /**
     * Valida si el usuario proporcionado existe en la base de datos.
     *
     * @param usuario Usuario a validar.
     * @return true si el usuario existe en la base de datos, false de lo contrario.
     */
    public boolean validarUsuario(Usuario usuario) {
        if (!openDatabase()) return false;

        String[] columns = {Constantes.NOMBRE, Constantes.CONTRASEÑA};
        String selection = Constantes.NOMBRE + " = ? AND " + Constantes.CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario.getNombre(), usuario.getContraseña()};
        @SuppressLint("Recycle") Cursor cursor = db.query(Constantes.NOMBRE_TABLA,
                columns, selection, selectionArgs,
                null, null, null);

        boolean resultado = cursor.moveToFirst();
        cursor.close();
        db.close();
        return resultado;
    }

    /**
     * Obtiene una lista de todos los usuarios en la base de datos.
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        if (!openDatabase()) return listaUsuarios;

        String[] columnas = {Constantes.ID, Constantes.NOMBRE, Constantes.CONTRASEÑA};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(Constantes.NOMBRE));
            @SuppressLint("Range") String contrasena = cursor.getString(cursor.getColumnIndex(Constantes.CONTRASEÑA));

            Usuario usuario = new Usuario(id, nombre, contrasena);
            listaUsuarios.add(usuario);
        }

        cursor.close();
        db.close();
        return listaUsuarios;
    }

    /**
     * Elimina un producto de la tabla de productos en la base de datos.
     *
     * @param producto Producto a borrar.
     */
    public void borrarProducto(Producto producto) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(producto.getId())};
        borrarRegistro(Constantes.NOMBRE_TABLA_PRODUCTO, whereClause, whereArgs);
    }

    /**
     * Obtiene una lista de todos los productos en la base de datos.
     *
     * @return Lista de productos.
     */
    public List<Producto> obtenerProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        if (!openDatabase()) return listaProductos;

        String[] columnas = {Constantes.ID, Constantes.PRODUCTOS, Constantes.DESCRIPCION_PRODUCTOS, Constantes.CANTIDAD_PRODUCTOS, Constantes.PRECIO};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_PRODUCTO, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") String productoNombre = cursor.getString(cursor.getColumnIndex(Constantes.PRODUCTOS));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(Constantes.DESCRIPCION_PRODUCTOS));
            @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(Constantes.PRECIO));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.CANTIDAD_PRODUCTOS));

            Producto producto = new Producto(id, productoNombre, precio, cantidad, descripcion);
            listaProductos.add(producto);
        }

        cursor.close();
        db.close();
        return listaProductos;
    }

    /**
     * Elimina una venta de la tabla de ventas en la base de datos.
     *
     * @param venta Venta a borrar.
     */
    public void borrarVenta(Venta venta) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(venta.getId())};
        borrarRegistro(Constantes.NOMBRE_TABLA_VENTA, whereClause, whereArgs);
    }

    /**
     * Obtiene una lista de todas las ventas en la base de datos.
     *
     * @return Lista de ventas.
     */
    public List<Venta> obtenerVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        if (!openDatabase()) return listaVentas;

        String[] columnas = {Constantes.ID, Constantes.VENTA_PRODUCTO,
                Constantes.VENTA_CANTIDAD,Constantes.VENTA_PRECIO_TOTAL, Constantes.VENTA_FECHA};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_VENTA, columnas, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Constantes.ID));
            @SuppressLint("Range") String nomProducto = cursor.getString(cursor.getColumnIndex(Constantes.VENTA_PRODUCTO));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.VENTA_CANTIDAD));
            @SuppressLint("Range") Double precio = cursor.getDouble(cursor.getColumnIndex(Constantes.VENTA_PRECIO_TOTAL));
            @SuppressLint("Range") String fechaString = cursor.getString(cursor.getColumnIndex(Constantes.VENTA_FECHA));

            // Convertir la fecha de tipo String a tipo Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = null;
            try{
                fecha = dateFormat.parse(fechaString);
            }catch (ParseException e){
                e.printStackTrace();
            }

            Venta venta = new Venta(id, nomProducto, cantidad, precio, fecha);
            listaVentas.add(venta);
        }

        cursor.close();
        db.close();
        return listaVentas;
    }

    /**
     * Obtiene un objeto Producto de la base de datos mediante su ID.
     *
     * @param id ID del producto.
     * @return Objeto Producto correspondiente al ID, o null si no se encuentra.
     */
    private Producto obtenerProductoPorId(int id) {
        if (!openDatabase()) return null;

        String[] columnas = {Constantes.ID, Constantes.PRODUCTOS, Constantes.DESCRIPCION_PRODUCTOS,
                Constantes.CANTIDAD_PRODUCTOS, Constantes.PRECIO};
        String selection = Constantes.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(Constantes.NOMBRE_TABLA_PRODUCTO, columnas, selection, selectionArgs, null, null, null);

        Producto producto = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String productoNombre = cursor.getString(cursor.getColumnIndex(Constantes.PRODUCTOS));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(Constantes.DESCRIPCION_PRODUCTOS));
            @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(Constantes.PRECIO));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex(Constantes.CANTIDAD_PRODUCTOS));

            producto = new Producto(productoNombre, precio, cantidad, descripcion);
        }

        cursor.close();
        db.close();
        return producto;
    }
    /**
     * Edita un usuario en la base de datos.
     *
     * @param usuario      El usuario a editar.
     * @param nombre       El nuevo nombre del usuario.
     * @param contraseña   La nueva contraseña del usuario.
     * @return             true si se editó el usuario exitosamente, false de lo contrario.
     */
    public boolean editarUsuario(Usuario usuario, String nombre, String contraseña) {
        if (!openDatabase()) {
            return false;
        }

        // Crear un objeto ContentValues para almacenar los nuevos valores del usuario
        ContentValues values = new ContentValues();
        values.put(Constantes.NOMBRE, nombre);
        values.put(Constantes.CONTRASEÑA, contraseña);

        // Construir la cláusula WHERE para identificar el usuario a editar
        String whereClause = Constantes.ID + " = ?";
        String[] whereArgs = {String.valueOf(usuario.getId())};

        // Actualizar el registro del usuario en la base de datos
        int rowsAffected = db.update(Constantes.NOMBRE_TABLA, values, whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();

        // Verificar si se afectaron filas (es decir, si se actualizó el usuario correctamente)
        return rowsAffected > 0;
    }


    /**
     * Edita un producto en la base de datos.
     *
     * @param producto           El producto a editar.
     * @param nombreProducto     El nuevo nombre del producto.
     * @param precioProducto     El nuevo precio del producto.
     * @param cantidadProducto   La nueva cantidad del producto.
     * @return                   true si se editó el producto exitosamente, false de lo contrario.
     */
    public boolean editarProducto(Producto producto, String nombreProducto,
                                  String precioProducto, String cantidadProducto) {
        if (!openDatabase()) {
            return false;
        }

        // Crear un objeto ContentValues para almacenar los nuevos valores del producto
        ContentValues values = new ContentValues();
        values.put(Constantes.PRODUCTOS, nombreProducto);
        values.put(Constantes.PRECIO, precioProducto);
        values.put(Constantes.CANTIDAD_PRODUCTOS, cantidadProducto);

        // Construir la cláusula WHERE para identificar el producto a editar
        String whereClause = Constantes.ID + " = ?";
        String[] whereArgs = {String.valueOf(producto.getId())};

        // Actualizar el registro del producto en la base de datos
        int rowsAffected = db.update(Constantes.NOMBRE_TABLA_PRODUCTO, values, whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();

        // Verificar si se afectaron filas (es decir, si se actualizó el producto correctamente)
        return rowsAffected > 0;
    }
    public boolean editarVenta(Venta venta, String nombreProducto,
                                  String precioProducto, String cantidadProducto) {
        if (!openDatabase()) {
            return false;
        }

        // Crear un objeto ContentValues para almacenar los nuevos valores del producto
        ContentValues values = new ContentValues();
        values.put(Constantes.PRODUCTOS, nombreProducto);
        values.put(Constantes.PRECIO, precioProducto);
        values.put(Constantes.CANTIDAD_PRODUCTOS, cantidadProducto);

        // Construir la cláusula WHERE para identificar el producto a editar
        String whereClause = Constantes.ID + " = ?";
        String[] whereArgs = {String.valueOf(venta.getId())};

        // Actualizar el registro del producto en la base de datos
        int rowsAffected = db.update(Constantes.NOMBRE_TABLA_VENTA, values, whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();

        // Verificar si se afectaron filas (es decir, si se actualizó el producto correctamente)
        return rowsAffected > 0;
    }
}
