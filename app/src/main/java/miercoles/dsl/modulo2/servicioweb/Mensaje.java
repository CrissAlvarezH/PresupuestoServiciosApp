package miercoles.dsl.modulo2.servicioweb;

import java.util.ArrayList;

import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Usuario;

public class Mensaje {
    private String mensaje;
    private Usuario usuario;
    private ArrayList<Obra> obras;
    private int id;

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
