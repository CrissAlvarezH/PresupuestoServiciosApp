package miercoles.dsl.modulo2.utilidades;

import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioWebUtils {
    public static ServicioWeb getServicioWeb(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create( ServicioWeb.class );
    }
}
