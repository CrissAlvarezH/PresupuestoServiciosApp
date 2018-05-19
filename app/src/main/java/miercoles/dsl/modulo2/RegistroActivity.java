package miercoles.dsl.modulo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Usuario;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNombre, edtCorreo, edtUsuario, edtPass, edtConfirmarPass;
    private Button btnRegistrarme;
    private ProgressBar progressBar;
    private ServicioWeb servicioWeb;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setToolbar();
        dbManager = new DBManager(this);

        edtNombre = findViewById(R.id.edt_nombre_registro);
        edtCorreo = findViewById(R.id.edt_correo_registro);
        edtUsuario = findViewById(R.id.edt_usuario_registro);
        edtPass = findViewById(R.id.edt_pass_registro);
        edtConfirmarPass = findViewById(R.id.edt_confirmar_pass);
        btnRegistrarme = findViewById(R.id.btn_registrarme);
        progressBar = findViewById(R.id.progress_registro);

        btnRegistrarme.setOnClickListener(this);

        servicioWeb = ServicioWebUtils.getServicioWeb();

    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_registro);

        if(toolbar != null){
            setSupportActionBar(toolbar);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Registro");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validarCampos() {
        if (edtNombre.getText().toString().trim().isEmpty()) {
            edtNombre.setError("Digite su nombre");
            return false;
        }

        if (edtCorreo.getText().toString().trim().isEmpty()) {
            edtCorreo.setError("Digite su correo");
            return false;
        }

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = edtCorreo.getText().toString().trim();

        Matcher mather = pattern.matcher(email);

        if(!mather.find()){
            edtCorreo.requestFocus();
            edtCorreo.setError("Email no valido");

            return false;
        }

        if (edtUsuario.getText().toString().trim().isEmpty()) {
            edtUsuario.setError("Digite su usuario");
            return false;
        }

        if (edtPass.getText().toString().trim().isEmpty()) {
            edtPass.setError("Difige una contraseña");
            return false;
        }

        if (edtConfirmarPass.getText().toString().trim().isEmpty()) {
            edtConfirmarPass.setError("Confirme su contraseña");
            return false;
        }

        if (!edtConfirmarPass.getText().toString().trim().equals(edtPass.getText().toString().trim())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void enviarRegistro() {
        btnRegistrarme.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        final String usuario = edtUsuario.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        final String correo = edtCorreo.getText().toString().trim();
        final String nombre = edtNombre.getText().toString().trim();

        Call<Mensaje> mensajeCall = servicioWeb.enviarRegistro(
                "registro",
                usuario,
                pass,
                correo,
                nombre
        );

        mensajeCall.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful()) {
                    Mensaje mensaje = response.body();

                    if (mensaje != null && mensaje.getMensaje() != null) {
                        if (mensaje.getMensaje().equals("okay")) {
                            Usuario yo = new Usuario();

                            yo.setId(mensaje.getId());
                            yo.setNombre(nombre);
                            yo.setCorreo(correo);
                            yo.setUsuario(usuario);

                            dbManager.insertarModelo(yo);

                            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(RegistroActivity.this, "Hubo un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                }

                btnRegistrarme.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegistroActivity.this, "Verifique su conexion y vuelva a intentar", Toast.LENGTH_SHORT).show();

                btnRegistrarme.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_registrarme:
                if(validarCampos()){
                    enviarRegistro();
                }

                break;
        }
    }
}
