package miercoles.dsl.modulo2.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "modulo_bd", null, 1);
    }

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_PRODUCTOS = "productos";
    public static final String TABLA_INSUMOS = "insumos";
    public static final String TABLA_OBRAS = "obras";
    public static final String TABLA_INSUMO_PRODUCTO = "insumo_producto";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String USUARIO = "usuario";
    public static final String CORREO = "correo";
    public static final String DESCRIPCION = "descripcion";
    public static final String UNIDAD_MEDIDA = "unidad_medida";
    public static final String ID_INSUMO = "id_insumo";
    public static final String ID_PRODUCTO = "id_producto";
    public static final String CANTIDAD_INSUMO = "cantidad_insumo";
    public static final String PRECIO = "precio";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_USUARIOS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT, " +
                USUARIO + " TEXT, " +
                CORREO + " TEXT)";

        String CREAR_TABLA_PRODUCTOS = "CREATE TABLE "+TABLA_PRODUCTOS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT, " +
                DESCRIPCION + " TEXT, " +
                UNIDAD_MEDIDA + " TEXT)";

        String CREAR_TABLA_INSUMOS = "CREATE TABLE "+TABLA_INSUMOS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT, " +
                DESCRIPCION + " TEXT, " +
                PRECIO + " REAL, " +
                UNIDAD_MEDIDA + " TEXT)";

        String CREAR_TABLA_OBRAS = "CREATE TABLE "+TABLA_OBRAS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT, " +
                DESCRIPCION + " TEXT)";

        String CREAR_TABLA_INSUMO_PRODUCTO = "CREATE TABLE " + TABLA_INSUMO_PRODUCTO +" ( "+
                ID_INSUMO + " INTEGER NOT NULL, " +
                ID_PRODUCTO + " INTEGER NOT NULL, " +
                CANTIDAD_INSUMO + " REAL NOT NULL, " +
                "FOREIGN KEY ("+ID_INSUMO+") REFERENCES "+TABLA_INSUMOS+"("+ID+"), " +
                "FOREIGN KEY ("+ID_PRODUCTO+") REFERENCES "+TABLA_PRODUCTOS+"("+ID+") )";

        db.execSQL(CREAR_TABLA_USUARIO);
        db.execSQL(CREAR_TABLA_PRODUCTOS);
        db.execSQL(CREAR_TABLA_INSUMOS);
        db.execSQL(CREAR_TABLA_OBRAS);
        db.execSQL(CREAR_TABLA_INSUMO_PRODUCTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}