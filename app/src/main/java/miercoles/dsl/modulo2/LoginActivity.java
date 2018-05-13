package miercoles.dsl.modulo2;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Usuario;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgLogo;
    private EditText edtUsuario;
    private EditText edtPass;
    private ServicioWeb servicioWeb;
    private Button btnEntrar;
    private LinearLayout layoutProgreso;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DBManager(this);

        imgLogo = findViewById(R.id.img_logo_login);
        edtUsuario = findViewById(R.id.edt_usuario);
        edtPass = findViewById(R.id.edt_pass);
        btnEntrar = findViewById(R.id.btn_entrar);
        layoutProgreso = findViewById(R.id.layout_progreso_login);

        btnEntrar.setOnClickListener(this);

        servicioWeb = ServicioWebUtils.getServicioWeb();

    }

    private boolean validarCampos(){
        if(edtUsuario.getText().toString().trim().isEmpty()){
            edtUsuario.setError("Ingrese un usuario");
            edtUsuario.requestFocus();
            return false;
        }

        if(edtPass.getText().toString().trim().isEmpty()){
            edtPass.setError("Ingrese su contraseña");
            edtPass.requestFocus();
            return false;
        }

        return true;
    }

    private void clickEntrar(){
        if(validarCampos()){
            layoutProgreso.setVisibility(View.VISIBLE);

            Call<Mensaje> mensajeCall = servicioWeb.login("login",
                    edtUsuario.getText().toString(),
                    edtPass.getText().toString());

            mensajeCall.enqueue(new Callback<Mensaje>() {
                @Override
                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                    if(response.isSuccessful()){
                        Mensaje mensaje = response.body();

                        if(mensaje != null && mensaje.getMensaje().equals("login okay")){
                            dbManager.limparBaseDatos();

                            Usuario usuario = mensaje.getUsuario();

                            dbManager.insertarModelo(usuario);
                            for(Obra obra : mensaje.getObras()){
                                dbManager.insertarModelo(obra);
                            }

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                    }

                    layoutProgreso.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Mensaje> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Revise su conexión y vuelva a intentar", Toast.LENGTH_SHORT).show();

                    layoutProgreso.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_entrar:
                clickEntrar();
                break;
        }
    }
}
