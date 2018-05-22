package miercoles.dsl.modulo2.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.PresupuestosAdapter;
import miercoles.dsl.modulo2.adaptadores.ProductosAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Presupuesto;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class DatosObraActivity extends AppCompatActivity implements View.OnClickListener, PresupuestosAdapter.ListenerClick{

    public static final String EXTRA_OBRA = "obra";
    private ArrayList<Producto> productos;
    private TextView txtNombre, txtTipo, txtDescripcion;
    private ProductosAdapter productosAdapter;
    private RecyclerView recyclerProductos;
    private RecyclerView recyclerPresupuestos;
    private TextView txtNoTienePresupuestos;
    private ServicioWeb servicioWeb;
    private DBManager dbManager;
    private ProgressBar progressBar;
    private Button btnAgregarPresupuesto;
    private Obra obra;
    private String TAG = "DatosDeObras";
    private ArrayList<Presupuesto> presupuestos;
    private PresupuestosAdapter presupuestoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_obra);

        Bundle paquete = getIntent().getExtras();

        if(paquete != null){
            obra = (Obra) paquete.getSerializable(EXTRA_OBRA);

            if(obra != null){
                setToolbar();
                dbManager = new DBManager(this);

                txtNombre = findViewById(R.id.txt_nombre_obra);
                txtTipo = findViewById(R.id.txt_tipo_obra);
                txtDescripcion = findViewById(R.id.txt_descripcion_obra);
                recyclerProductos = findViewById(R.id.recycler_productos_obra);
                txtNoTienePresupuestos = findViewById(R.id.txt_no_tiene_presuspuestos);
                recyclerPresupuestos = findViewById(R.id.recycler_presupuestos_obra);
                progressBar = findViewById(R.id.progress_agregar_presupuesto);
                btnAgregarPresupuesto = findViewById(R.id.btn_agregar_presupuesto);

                recyclerPresupuestos.setNestedScrollingEnabled(false);
                recyclerProductos.setNestedScrollingEnabled(false);

                btnAgregarPresupuesto.setOnClickListener(this);

                RecyclerView.LayoutManager lmProductos = new LinearLayoutManager(this);
                recyclerProductos.setLayoutManager(lmProductos);

                RecyclerView.LayoutManager lmPresupuestos = new LinearLayoutManager(this);
                recyclerPresupuestos.setLayoutManager(lmPresupuestos);

                productos = obra.getProductos();

                if(productos != null) {
                    productosAdapter = new ProductosAdapter(productos, new ListenerProductos(), ProductosAdapter.TIPO_CANTIDAD);
                    recyclerProductos.setAdapter(productosAdapter);
                }else{
                    productos = new ArrayList<>();
                }

                presupuestos = dbManager.getPresupuestos(obra.getId());

                if(presupuestos != null){
                    if(presupuestos.size() > 0){
                        txtNoTienePresupuestos.setVisibility(View.INVISIBLE);
                    }else{
                        txtNoTienePresupuestos.setVisibility(View.VISIBLE);
                    }
                }

                presupuestoAdapter = new PresupuestosAdapter(presupuestos, this);
                recyclerPresupuestos.setAdapter(presupuestoAdapter);


                txtNombre.setText( obra.getNombre() );
                txtTipo.setText( obra.getTipo() );
                txtDescripcion.setText( obra.getDescripcion() );

                servicioWeb = ServicioWebUtils.getServicioWeb();
            }else{
                finish();
                Toast.makeText(this, "Obra nula", Toast.LENGTH_SHORT).show();
            }
        }else{
            finish();
            Toast.makeText(this, "Obra no definida", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void longClickItem(final Presupuesto presupuesto, int posicion) {
        AlertDialog.Builder builderAialogoAnomalia = new AlertDialog.Builder(this);

        builderAialogoAnomalia.setMessage("¿Desea borrar este presupuesto?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pedirBorrarPresupuesto(presupuesto.getId());

                    }
                }).setNegativeButton("NO", null);

        // creamos el dialogo
        AlertDialog dialogoAnomalia = builderAialogoAnomalia.create();
        dialogoAnomalia.show();// mostramos el dialogo
    }

    private class ListenerProductos implements ProductosAdapter.ListenerClick {

        @Override
        public void clickItem(Producto producto, int posicion) {
            Intent intent = new Intent(DatosObraActivity.this, DetalleListadoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetalleListadoActivity.TIPO, DetalleListadoActivity.TIPO_PRODUCTO);
            bundle.putSerializable(DetalleListadoActivity.ARGS_PRODUCTO, producto);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_datos_obra);

        if(toolbar != null){
            setSupportActionBar(toolbar);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Datos de la obra");
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

    private void refreshPresupuestos(){
        presupuestos = dbManager.getPresupuestos(obra.getId());

        if(presupuestos.size() > 0){
            txtNoTienePresupuestos.setVisibility(View.INVISIBLE);
        }else{
            txtNoTienePresupuestos.setVisibility(View.VISIBLE);
        }

        presupuestoAdapter.setPresupuestos(presupuestos);
    }

    private void enviarPresupuesto(){
        if(productos.size() > 0){
            btnAgregarPresupuesto.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            final float precioDeObra = productosAdapter.calcularPrecioTotal();
            final String fechaHoy = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA).format(new Date());

            Call<Mensaje> mensajeCall = servicioWeb.agregarPresupuesto(
                    "agregarPresupuesto",
                    precioDeObra,
                    obra.getId(),
                    fechaHoy
            );

            mensajeCall.enqueue(new Callback<Mensaje>() {
                @Override
                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                    if(response.isSuccessful()){
                        // Si todoo sale bien guardamos el presupuesto localmente
                        Mensaje mensaje = response.body();

                        if(mensaje != null){
                            int idPresupuesto = mensaje.getId();
                            Presupuesto presupuesto = new Presupuesto(
                                    idPresupuesto,
                                    obra.getId(),
                                    precioDeObra,
                                    fechaHoy
                            );

                            dbManager.insertarModelo(presupuesto);

                            refreshPresupuestos();
                        }else{
                            Toast.makeText(DatosObraActivity.this, "Respuesta del server nula", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(DatosObraActivity.this, "Ocurrió un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                    }

                    btnAgregarPresupuesto.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Mensaje> call, Throwable t) {
                    t.printStackTrace();

                    Toast.makeText(DatosObraActivity.this, "Comprueve su conexion y vuelta a intentar", Toast.LENGTH_SHORT).show();

                    btnAgregarPresupuesto.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });

        }else{
            Toast.makeText(this, "Usted no tiene productos en esta obra", Toast.LENGTH_SHORT).show();
        }
    }

    private void pedirBorrarPresupuesto(final int idPresupuesto){
        btnAgregarPresupuesto.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerPresupuestos.setVisibility(GONE);

        Call<Mensaje> mensajeCall = servicioWeb.borrarPresupuesto(
                "borrarPresupuesto",
                idPresupuesto + ""
        );

        mensajeCall.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if(response.isSuccessful()){
                    Mensaje mensaje = response.body();

                    if(mensaje != null){
                        if(mensaje.getMensaje().equals("okay")){
                            dbManager.borrarPresupuesto(idPresupuesto);

                            refreshPresupuestos();
                        }
                    }
                }else{
                    Toast.makeText(DatosObraActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                }

                btnAgregarPresupuesto.setVisibility(View.VISIBLE);
                progressBar.setVisibility(GONE);
                recyclerPresupuestos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DatosObraActivity.this, "Verifique su conexión", Toast.LENGTH_SHORT).show();

                btnAgregarPresupuesto.setVisibility(View.VISIBLE);
                progressBar.setVisibility(GONE);
                recyclerPresupuestos.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_agregar_presupuesto:
                AlertDialog.Builder builderAialogoAnomalia = new AlertDialog.Builder(this);

                builderAialogoAnomalia.setMessage("¿Desea agregar un presupuesto para el dia de hoy?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            enviarPresupuesto();
                            }
                        }).setNegativeButton("CANCELAR", null);

                // creamos el dialogo
                AlertDialog dialogoAnomalia = builderAialogoAnomalia.create();
                dialogoAnomalia.show();// mostramos el dialogo
                break;
        }
    }
}
