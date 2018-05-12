package miercoles.dsl.modulo2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import miercoles.dsl.modulo2.LoginActivity;
import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Usuario;


public class MiPerfilFragment extends Fragment implements View.OnClickListener{

    public MiPerfilFragment() {
        //
    }

    private DBManager dbManager;

    private Button btnCerrarSesion;
    private TextView txtUsuario, txtCorreo, txtNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        dbManager = new DBManager(getContext());

        Usuario usuario = dbManager.getUsuario();

        if(usuario != null){
            txtUsuario = vista.findViewById(R.id.txt_usuario);
            txtCorreo = vista.findViewById(R.id.txt_correo);
            txtNombre = vista.findViewById(R.id.txt_nombre);
            btnCerrarSesion = vista.findViewById(R.id.btn_cerrar_sesion);

            btnCerrarSesion.setOnClickListener(this);

            txtUsuario.setText( usuario.getUsuario() );
            txtCorreo.setText( usuario.getCorreo() );
            txtNombre.setText( usuario.getNombre() );
        }

        return vista;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cerrar_sesion:
                final Activity actividad = getActivity();
                if(actividad != null) {
                    AlertDialog.Builder builderAialogoAnomalia = new AlertDialog.Builder(actividad);

                    builderAialogoAnomalia.setMessage("¿Esta seguro de cerrar sesión?")
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dbManager.borrarUsuario();

                                    Intent intent = new Intent(getContext(), LoginActivity.class);
                                    startActivity(intent);

                                    actividad.finish();

                                }
                            }).setNegativeButton("NO", null);

                    // creamos el dialogo
                    AlertDialog dialogoAnomalia = builderAialogoAnomalia.create();
                    dialogoAnomalia.show();// mostramos el dialogo
                }
                break;
        }
    }
}
