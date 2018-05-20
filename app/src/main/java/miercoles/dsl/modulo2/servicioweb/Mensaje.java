package miercoles.dsl.modulo2.servicioweb;

import java.util.ArrayList;

import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.modelos.ProductoObra;
import miercoles.dsl.modulo2.modelos.Usuario;

public class Mensaje {
    private String mensaje;
    private Usuario usuario;
    private ArrayList<Obra> obras;
    private ArrayList<Producto> productos;
    private ArrayList<ProductoObra> producto_obra;
    private int id;

    public ArrayList<ProductoObra> getProducto_obra() {
        return producto_obra;
    }

    public void setProducto_obra(ArrayList<ProductoObra> producto_obra) {
        this.producto_obra = producto_obra;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Obra> getObras() {
        return obras;
    }

    public void setObras(ArrayList<Obra> obras) {
        this.obras = obras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
