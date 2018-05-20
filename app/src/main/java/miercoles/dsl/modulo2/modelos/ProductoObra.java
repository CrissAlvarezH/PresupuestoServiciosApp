package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import static miercoles.dsl.modulo2.basedatos.DBHelper.CANTIDAD_PRODUCTO;
import static miercoles.dsl.modulo2.basedatos.DBHelper.ID_OBRA;
import static miercoles.dsl.modulo2.basedatos.DBHelper.ID_PRODUCTO;
import static miercoles.dsl.modulo2.basedatos.DBHelper.TABLA_PRODUCTO_OBRA;

public class ProductoObra implements BaseModelo{
    private int producto_id, obra_id;
    private float cantidad_producto;

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID_PRODUCTO, producto_id);
        values.put(ID_OBRA, obra_id);
        values.put(CANTIDAD_PRODUCTO, cantidad_producto);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_PRODUCTO_OBRA;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getObra_id() {
        return obra_id;
    }

    public void setObra_id(int obra_id) {
        this.obra_id = obra_id;
    }

    public float getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(float cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }


}
