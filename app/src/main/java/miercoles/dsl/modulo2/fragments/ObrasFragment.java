package miercoles.dsl.modulo2.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.AgregarObraActivity;
import miercoles.dsl.modulo2.LoginActivity;
import miercoles.dsl.modulo2.MainActivity;
import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.ObrasAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;


public class ObrasFragment extends Fragment {

    public ObrasFragment() {
        //
    }

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
            Toast.makeText(getContext(), "Click en "+obra.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarObra(){
        Intent intent = new Intent(getContext(), AgregarObraActivity.class);
        startActivity(intent);
    }
}
