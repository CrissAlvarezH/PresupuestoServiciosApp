package miercoles.dsl.modulo2.servicioweb;

import miercoles.dsl.modulo2.utilidades.Constantes;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServicioWeb {

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> login(@Field("accion") String accion,
                        @Field("usuario") String usuario,
                        @Field("pass") String pass);

}
