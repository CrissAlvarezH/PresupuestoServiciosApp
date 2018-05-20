package miercoles.dsl.modulo2.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.ProductosAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosActivity extends AppCompatActivity  implements View.OnClickListener{

    public static final String ARG_PRODUCTO = "prod";

    private RecyclerView recyclerProductos;
    private LinearLayout layoutReintentar;
    private Button btnReintentar;
    private TextView txtProblema;
    private ProductosAdapter productosAdapter;
    private ServicioWeb servicioWeb;
    private DBManager dbManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        setToolbar();

        dbManager = new DBManager(this);

        progressBar = findViewById(R.id.progress_productos);
        layoutReintentar = findViewById(R.id.layout_reintentar);
        btnReintentar = findViewById(R.id.btn_reintentar);
        txtProblema = findViewById(R.id.txt_problema);
        recyclerProductos = findViewById(R.id.recycler_productos);

        btnReintentar.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProductos.setLayoutManager(layoutManager);

        productosAdapter = new ProductosAdapter(new ArrayList<Producto>(),
                                                new EscuchadorClick(), ProductosAdapter.TIPO_LISTAR);
        recyclerProductos.setAdapter(productosAdapter);

        servicioWeb = ServicioWebUtils.getServicioWeb();

        pedirProductos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reintentar:
                pedirProductos();
                break;
        }
    }

    private class EscuchadorClick implements ProductosAdapter.ListenerClick{

        @Override
        public void clickItem(final Producto producto, int posicion) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ProductosActivity.this);

            LayoutInflater layoutInflater = getLayoutInflater();
            View layout = layoutInflater.inflate(R.layout.dialog_cantidad, null);
            final EditText edtCantidad = layout.findViewById(R.id.edt_cantidad);

            builder.setView(layout)
                    .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String cantidadTxt = edtCantidad.getText().toString();

                            try {
                                float cantidad = Float.parseFloat(cantidadTxt);

                                if(cantidad > 0f) {
                                    Intent intent = new Intent();
                                    Bundle paquete = new Bundle();

                                    producto.setCantidad(cantidad);

                                    paquete.putSerializable(ARG_PRODUCTO, producto);
                                    intent.putExtras(paquete);

                                    setResult(RESULT_OK, intent);
                                    finish();
                                }else{
                                    Toast.makeText(ProductosActivity.this, "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show();
                                }
                            }catch (NumberFormatException e){
                                Toast.makeText(ProductosActivity.this, "Ingrese un numero valido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", null);
            builder.create().show();
        }
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_productos);

        if(toolbar != null){
            setSupportActionBar(toolbar);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Productos");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void pedirProductos(){
        progressBar.setVisibility(View.VISIBLE);
        layoutReintentar.setVisibility(View.GONE);

        Call<ArrayList<Producto>> productosCall = servicioWeb.getProductos("productos");

        productosCall.enqueue(new Callback<ArrayList<Producto>>() {
            @Override
            public void onResponse(Call<ArrayList<Producto>> call, Response<ArrayList<Producto>> response) {
                if(response.isSuccessful()){
                    ArrayList<Producto> productos = response.body();

                    if(productos != null && productos.size() > 0){
                        productosAdapter.setProductos(productos);

                        layoutReintentar.setVisibility(View.GONE);
                    }else{
                        txtProblema.setText("No hay productos");
                        layoutReintentar.setVisibility(View.VISIBLE);
                    }

                }else{
                    txtProblema.setText("Ocurrió un problema");
                    layoutReintentar.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Producto>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txtProblema.setText("Revise su conexión y vuelva a intentar");
                layoutReintentar.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
