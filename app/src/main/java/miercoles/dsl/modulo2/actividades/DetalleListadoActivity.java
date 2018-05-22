package miercoles.dsl.modulo2.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.modelos.Insumo;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleListadoActivity extends AppCompatActivity {
    // Si se va a describir un producto y a lsitar sus insumos
    public static final String TIPO_PRODUCTO = "producto";
    public static final String ARGS_PRODUCTO = "args_producto";

    // Si se va a describir un insumo y a listar sus precios
    public static final String TIPO_INSUMO = "insumo";
    public static final String ARGS_INSUMO = "args_insumo";

    // Nombre del parametro
    public static final String TIPO = "tipo";

    private ImageView imgDetalle;
    private TextView txtTitulo, txtTituloListado, txtDescripcion;
    private RecyclerView recyclerView;
    private TextView txtAnuncio;
    private ServicioWeb servicioWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_listado);
        setToolbar();

        imgDetalle = findViewById(R.id.img_descripcion);
        txtTitulo = findViewById(R.id.txt_titulo);
        txtTituloListado = findViewById(R.id.txt_titulo_listado);
        txtDescripcion = findViewById(R.id.txt_descripcion);
        txtAnuncio = findViewById(R.id.txt_anuncio);
        recyclerView = findViewById(R.id.recycler_listado);

        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager lmListado = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmListado);

        servicioWeb = ServicioWebUtils.getServicioWeb();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.getString(TIPO) != null){
            String tipo = bundle.getString(TIPO);

            switch (tipo){
                case TIPO_PRODUCTO:
                    Producto producto = (Producto) bundle.getSerializable(ARGS_PRODUCTO);
                    imgDetalle.setImageResource(R.drawable.ic_maletin);

                    if(producto != null) {
                        txtTitulo.setText( producto.getNombre() );
                        txtDescripcion.setText( producto.getDescripcion() );
                        txtTituloListado.setText("Insumos");

                    }
                    break;
                case TIPO_INSUMO:
                    Insumo insumo = (Insumo) bundle.getSerializable(ARGS_INSUMO);
                    imgDetalle.setImageResource(R.drawable.ic_money);

                    if(insumo != null){
                        txtTitulo.setText( insumo.getNombre() );
                        txtDescripcion.setText( insumo.getDescripcion() );
                        txtTituloListado.setText( "Precios" );
                    }

                    break;
            }
        }



    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_detalle_listado);

        if(toolbar != null){
            setSupportActionBar(toolbar);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Detalles");
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

    private void pedirInsumos(int idProducto){
        txtAnuncio.setVisibility(View.VISIBLE);
        txtAnuncio.setText("Cargando...");

        Call<Mensaje> mensajeCall = servicioWeb.getInsumosDeUnProducto(
                "insumosDeUnProducto",
                idProducto + ""
        );

        mensajeCall.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if(response.isSuccessful()){
                    Mensaje mensaje = response.body();

                    if(mensaje != null){
                        ArrayList<Insumo> insumos = mensaje.getInsumos();
                    }
                }

                txtAnuncio.setText("");
                txtAnuncio.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                txtAnuncio.setText("Revise su conexión e intente mas tarde");
            }
        });

    }
}
