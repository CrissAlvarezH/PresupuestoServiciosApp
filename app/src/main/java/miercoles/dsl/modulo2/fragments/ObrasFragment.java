package miercoles.dsl.modulo2.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.actividades.AgregarObraActivity;
import miercoles.dsl.modulo2.actividades.DatosObraActivity;
import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.actividades.LoginActivity;
import miercoles.dsl.modulo2.adaptadores.ObrasAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ObrasFragment extends Fragment {

    public ObrasFragment() {
        //
    }

    private ServicioWeb servicioWeb;

    private FloatingActionButton btnAgregar;
    private ArrayList<Obra> obras;
    private ObrasAdapter obrasAdapter;

    private DBManager dbManager;
    private RecyclerView recyclerObras;
    private TextView txtNoTieneObras;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_obras, container, false);

        dbManager = new DBManager(getContext());

        txtNoTieneObras = vista.findViewById(R.id.txt_no_tiene_obras);
        recyclerObras = vista.findViewById(R.id.recycler_obras);
        btnAgregar = vista.findViewById(R.id.btn_agregar_obra);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarObra();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerObras.setLayoutManager(layoutManager);

        obrasAdapter = new ObrasAdapter(new ArrayList<Obra>(), new MiListener());

        recyclerObras.setAdapter(obrasAdapter);

        servicioWeb = ServicioWebUtils.getServicioWeb();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshObras();
    }

    private void refreshObras(){
        obras = dbManager.getObras();

        if(obras.size() > 0){
            obrasAdapter.setObras( obras );
            txtNoTieneObras.setVisibility(View.GONE);
        }
    }

    private class MiListener implements ObrasAdapter.ListenerClick{

        @Override
        public void clickItem(Obra obra, int posicion) {
            Intent intent = new Intent(getContext(), DatosObraActivity.class);
            Bundle paquete = new Bundle();
            paquete.putSerializable(DatosObraActivity.EXTRA_OBRA, obra);
            intent.putExtras(paquete);
            startActivity(intent);
        }

        @Override
        public void longClickItem(final Obra obra, int posicion) {
            AlertDialog.Builder builderAialogoAnomalia = new AlertDialog.Builder(getContext());

            builderAialogoAnomalia.setMessage("¿Desea borrar esta obra?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        pedirBorrarObra(obra.getId());

                        }
                    }).setNegativeButton("NO", null);

            // creamos el dialogo
            AlertDialog dialogoAnomalia = builderAialogoAnomalia.create();
            dialogoAnomalia.show();// mostramos el dialogo
        }
    }

    private void agregarObra(){
        Intent intent = new Intent(getContext(), AgregarObraActivity.class);
        startActivity(intent);
    }

    private void pedirBorrarObra(final int idObra){
        recyclerObras.setVisibility(View.GONE);
        txtNoTieneObras.setText("Cargando...");
        txtNoTieneObras.setVisibility(View.VISIBLE);


        final Call<Mensaje> mensajeCall = servicioWeb.borrarObra(
                "borrarObra",
                idObra + ""
        );

        mensajeCall.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if(response.isSuccessful()){
                    Mensaje mensaje = response.body();

                    if(mensaje != null){
                        if(mensaje.getMensaje().equals("okay")){
                            dbManager.borrarObra(idObra);

                            refreshObras();
                        }
                    }else{
                        Toast.makeText(getContext(), "Ocurrió un problema", Toast.LENGTH_SHORT).show();
                    }
                }

                recyclerObras.setVisibility(View.VISIBLE);
                txtNoTieneObras.setText("No tiene obras");
                txtNoTieneObras.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();

                recyclerObras.setVisibility(View.VISIBLE);
                txtNoTieneObras.setText("No tiene obras");
                txtNoTieneObras.setVisibility(View.GONE);
            }
        });
    }
}
