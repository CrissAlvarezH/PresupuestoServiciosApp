package miercoles.dsl.modulo2.modelos;

import android.content.ContentValues;

public interface BaseModelo {
    ContentValues toContentValues();
    String getNombreTabla();
}
