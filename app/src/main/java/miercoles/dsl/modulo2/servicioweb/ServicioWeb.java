package miercoles.dsl.modulo2.servicioweb;

import java.util.ArrayList;

import miercoles.dsl.modulo2.modelos.Producto;
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

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<ArrayList<Producto>> getProductos(@Field("accion") String accion);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> agregarObra(@Field("accion") String accion,
                              @Field("idUsuario") String idUsuario,
                              @Field("nombre") String nombre,
                              @Field("descripcion") String descripcion,
                              @Field("tipo") String tipo,
                              @Field("idsProductos") String idsProductos,
                              @Field("cantidades") String cantidades);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje>  enviarRegistro(@Field("accion") String accion,
                                  @Field("usuario") String usuario,
                                  @Field("pass") String pass,
                                  @Field("correo") String correo,
                                  @Field("nombre") String nombre);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> agregarPresupuesto(@Field("accion") String accion,
                                     @Field("precio") float precio,
                                     @Field("idObra") float idObra,
                                     @Field("fecha") String fecha);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> getInsumosDeUnProducto(@Field("accion") String accion,
                                         @Field("idProducto") String idProducto);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> getPreciosDeUnInsumo(@Field("accion") String accion,
                                       @Field("idInsumo") String idInsumo);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> borrarObra(@Field("accion") String accion,
                             @Field("idObra") String idObra);

    @FormUrlEncoded
    @POST(Constantes.URL_INDEX)
    Call<Mensaje> borrarPresupuesto(@Field("accion") String accion,
                                    @Field("idPresupuesto") String idPresupuesto);
}
