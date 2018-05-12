package miercoles.dsl.modulo2.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import miercoles.dsl.modulo2.modelos.BaseModelo;
import miercoles.dsl.modulo2.modelos.Obra;
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
            usuario.setContrasena( cursor.getString( cursor.getColumnIndex( CORREO ) ) );
        }

        return usuario;
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
                        "SELECT "+TABLA_PRODUCTOS+".* FROM "+TABLA_PRODUCTOS+","+TABLA_PRODUCTO_OBRA+" WHERE "+ID_PRODUCTO+"="+ID+" AND "+ID_OBRA+"=?",
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
}