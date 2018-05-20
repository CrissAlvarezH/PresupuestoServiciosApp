package miercoles.dsl.modulo2.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import miercoles.dsl.modulo2.modelos.BaseModelo;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Presupuesto;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.modelos.Usuario;

import static miercoles.dsl.modulo2.basedatos.DBHelper.*;

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context contexto) {
        this.dbHelper = new DBHelper(contexto);
    }

    public void insertarModelo(BaseModelo modelo){
        database = dbHelper.getWritableDatabase();

        database.insert(modelo.getNombreTabla(), null, modelo.toContentValues());
    }

    public Usuario getUsuario(){
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLA_USUARIOS, null);

        Usuario usuario = null;

        if(cursor.moveToFirst()){
            usuario = new Usuario();

            usuario.setId( cursor.getInt( cursor.getColumnIndex( ID ) ) );
            usuario.setNombre( cursor.getString( cursor.getColumnIndex( NOMBRE ) ) );
            usuario.setUsuario( cursor.getString( cursor.getColumnIndex( USUARIO ) ) );
            usuario.setCorreo( cursor.getString( cursor.getColumnIndex( CORREO ) ) );
        }

        cursor.close();

        return usuario;
    }

    public void borrarUsuario(){
        database = dbHelper.getReadableDatabase();

        database.delete(TABLA_USUARIOS, null, null);
    }

    public ArrayList<Presupuesto> getPresupuestos(int idObra){
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLA_PRESUPUESTOS+" WHERE "+ID_OBRA+"=?",
                new String[]{idObra+""});

        ArrayList<Presupuesto> presupuestos = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                Presupuesto presupuesto = new Presupuesto(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getInt(cursor.getColumnIndex(ID_OBRA)),
                        cursor.getFloat(cursor.getColumnIndex(PRECIO)),
                        cursor.getString(cursor.getColumnIndex(FECHA))
                );

                presupuestos.add(presupuesto);
            }while (cursor.moveToNext());
        }

        cursor.close();

        return presupuestos;
    }

    public ArrayList<Obra> getObras(){
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLA_OBRAS, null);

        ArrayList<Obra> obras = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Obra obra = new Obra();

                obra.setId( cursor.getInt( cursor.getColumnIndex(ID) ) );
                obra.setNombre( cursor.getString( cursor.getColumnIndex(NOMBRE) ) );
                obra.setDescripcion( cursor.getString( cursor.getColumnIndex(DESCRIPCION ) ) );
                obra.setTipo( cursor.getString( cursor.getColumnIndex(TIPO) ) );

                Cursor cPro = database.rawQuery(
                        "SELECT "+TABLA_PRODUCTOS+".* ,"+TABLA_PRODUCTO_OBRA+"."+CANTIDAD_PRODUCTO+" FROM "+TABLA_PRODUCTOS+","+TABLA_PRODUCTO_OBRA+" WHERE "+ID_PRODUCTO+"="+ID+" AND "+ID_OBRA+"=?",
                        new String[]{obra.getId()+""}
                );

                ArrayList<Producto> productos = new ArrayList<>();

                if(cPro.moveToFirst()){
                    do{
                        Producto producto = new Producto();

                        producto.setId( cPro.getInt( cPro.getColumnIndex(ID) ) );
                        producto.setNombre( cPro.getString( cPro.getColumnIndex(NOMBRE) ) );
                        producto.setDescripcion( cPro.getString( cPro.getColumnIndex(DESCRIPCION) ) );
                        producto.setUnidad_medida( cPro.getString( cPro.getColumnIndex(UNIDAD_MEDIDA) ) );
                        producto.setPrecio( cPro.getFloat( cPro.getColumnIndex(PRECIO) ) );
                        producto.setCantidad( cPro.getFloat( cPro.getColumnIndex(CANTIDAD_PRODUCTO) ) );

                        productos.add( producto );
                    }while (cPro.moveToNext());

                    obra.setProductos(productos);
                }

                cPro.close();

                obras.add(obra);
            }while (cursor.moveToNext());
        }

        cursor.close();

        return obras;
    }

    public void limparBaseDatos(){
        database = dbHelper.getWritableDatabase();

        database.delete(TABLA_USUARIOS, null, null);
        database.delete(TABLA_OBRAS, null, null);
        database.delete(TABLA_PRODUCTOS, null, null);
        database.delete(TABLA_INSUMOS, null, null);
        database.delete(TABLA_PRODUCTO_OBRA, null, null);
        database.delete(TABLA_INSUMO_PRODUCTO, null, null);
        database.delete(TABLA_PRESUPUESTOS, null, null);
    }
}
