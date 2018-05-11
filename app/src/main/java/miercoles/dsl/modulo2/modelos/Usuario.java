package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

import miercoles.dsl.modulo2.basedatos.DBHelper;

public class Usuario implements BaseModelo{
    private int id;
    private String usuario, nombre, contrasena, correo;

    public Usuario(int id, String usuario, String nombre, String contrasena, String correo) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(DBHelper.ID, id);
        values.put(DBHelper.USUARIO, usuario);
        values.put(DBHelper.NOMBRE, nombre);
        values.put(DBHelper.CORREO, correo);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return DBHelper.TABLA_USUARIOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
