package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import static miercoles.dsl.modulo2.basedatos.DBHelper.FECHA;
import static miercoles.dsl.modulo2.basedatos.DBHelper.ID;
import static miercoles.dsl.modulo2.basedatos.DBHelper.ID_OBRA;
import static miercoles.dsl.modulo2.basedatos.DBHelper.PRECIO;
import static miercoles.dsl.modulo2.basedatos.DBHelper.TABLA_PRESUPUESTOS;

public class Presupuesto implements BaseModelo{
    private int id, obra_id;
    private float precio;
    private String fecha;

    public Presupuesto(int id, int obra_id, float precio, String fecha) {
        this.id = id;
        this.obra_id = obra_id;
        this.precio = precio;
        this.fecha = fecha;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(FECHA, fecha);
        values.put(PRECIO, precio);
        values.put(ID_OBRA, obra_id);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_PRESUPUESTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObra_id() {
        return obra_id;
    }

    public void setObra_id(int obra_id) {
        this.obra_id = obra_id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
