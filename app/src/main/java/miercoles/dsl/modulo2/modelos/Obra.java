package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

import miercoles.dsl.modulo2.basedatos.DBHelper;

public class Obra implements BaseModelo, Serializable{
    private int id;
    private String nombre, tipo, descripcion;
    private ArrayList<Producto> productos;

    public Obra(String nombre, String tipo, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Obra(){}

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(DBHelper.ID, id);
        values.put(DBHelper.NOMBRE, nombre);
        values.put(DBHelper.TIPO, tipo);
        values.put(DBHelper.DESCRIPCION, descripcion);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return DBHelper.TABLA_OBRAS;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
