package miercoles.dsl.modulo2.modelos;

public class Precio {
    private int id, insumo_id;
    private String fecha, precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInsumo_id() {
        return insumo_id;
    }

    public void setInsumo_id(int insumo_id) {
        this.insumo_id = insumo_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
