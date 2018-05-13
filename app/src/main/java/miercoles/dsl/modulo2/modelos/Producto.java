package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import java.util.ArrayList;

import miercoles.dsl.modulo2.basedatos.DBHelper;

public class Producto implements BaseModelo {
    private int id;
    private String nombre, descripcion, unidad_medida;
    private ArrayList<Insumo> insumos;
    private float precio, cantidad;


    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(DBHelper.ID, id);
        values.put(DBHelper.NOMBRE, nombre);
        values.put(DBHelper.DESCRIPCION, descripcion);
        values.put(DBHelper.UNIDAD_MEDIDA, unidad_medida);
        values.put(DBHelper.PRECIO, precio);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return DBHelper.TABLA_PRODUCTOS;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Insumo> getInsumos() {
        return insumos;
    }

    public void setInsumos(ArrayList<Insumo> insumos) {
        this.insumos = insumos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }


}
