package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import miercoles.dsl.modulo2.basedatos.DBHelper;

public class Insumo implements BaseModelo{
    private int id;
    private String nombre, descripcion, precio, unidad_medida;


    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(DBHelper.ID, id);
        values.put(DBHelper.NOMBRE, nombre);
        values.put(DBHelper.PRECIO, precio);
        values.put(DBHelper.DESCRIPCION, descripcion);
        values.put(DBHelper.UNIDAD_MEDIDA, unidad_medida);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return DBHelper.TABLA_INSUMOS;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

}
