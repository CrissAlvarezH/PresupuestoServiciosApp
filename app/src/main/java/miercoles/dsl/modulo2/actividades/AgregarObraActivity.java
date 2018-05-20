package miercoles.dsl.modulo2.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.ProductosAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Producto;
import miercoles.dsl.modulo2.servicioweb.Mensaje;
import miercoles.dsl.modulo2.servicioweb.ServicioWeb;
import miercoles.dsl.modulo2.utilidades.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarObraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int COD_ADD_PRODUCTO = 243;

    private EditText edtNombre, edtDescripcion;
    private Spinner spnTipo;
    private RecyclerView recyclerProductos;
    private ProductosAdapter productosAdapter;
    private TextView txtNoTieneProd;
    private Button btnGuardarObra, btnAgregarTrabajo;
    private DBManager dbManager;
    private ServicioWeb servicioWeb;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_obra);
        setToolbar();

        dbManager = new DBManager(this);

        progress = findViewById(R.id.progress_agregar_obra);
        txtNoTieneProd = findViewById(R.id.txt_no_tiene_prod);
        edtNombre = findViewById(R.id.edt_nombre_obra);
        edtDescripcion = findViewById(R.id.edt_descripcion_obra);
        spnTipo = findViewById(R.id.spn_tipo_obra);
        btnAgregarTrabajo = findViewById(R.id.btn_agregar_producto);
        btnGuardarObra = findViewById(R.id.btn_guardar_obra);

        recyclerProductos = findViewById(R.id.recycler_productos_obra);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProductos.setLayoutManager(layoutManager);
        recyclerProductos.setNestedScrollingEnabled(false);

        productosAdapter = new ProductosAdapter(new ArrayList<Producto>(),
                                    new EscuchadorClick(), ProductosAdapter.TIPO_CANTIDAD);
        recyclerProductos.setAdapter(productosAdapter);

        btnAgregarTrabajo.setOnClickListener(this);
        btnGuardarObra.setOnClickListener(this);

        servicioWeb = ServicioWebUtils.getServicioWeb();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_agregar_obra);

        if(toolbar != null){
            setSupportActionBar(toolbar);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Agregar obra");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }



    private class EscuchadorClick implements ProductosAdapter.ListenerClick{
        @Override
        public void clickItem(Producto producto, int posicion) {

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

    private boolean validarCampos(){
        if(edtNombre.getText().toString().trim().isEmpty()){
            edtNombre.setError("Escriba el nombre de la obra");
            edtNombre.requestFocus();

            return false;
        }

        if(productosAdapter.getItemCount() == 0){
            Toast.makeText(this, "Debe agregar productos", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_agregar_producto:
                Intent intent = new Intent(this, ProductosActivity.class);
                startActivityForResult(intent, COD_ADD_PRODUCTO);
                break;
            case R.id.btn_guardar_obra:
                if(validarCampos()){
                    btnGuardarObra.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                    final String nombre = edtNombre.getText().toString();
                    final String descripcion = edtDescripcion.getText().toString();
                    final String tipo = spnTipo.getSelectedItem().toString().toLowerCase();

                    Call<Mensaje> mensajeCall = servicioWeb.agregarObra("agregarObra",
                            dbManager.getUsuario().getId()+"",
                            nombre, descripcion, tipo,
                            productosAdapter.getIdsProductos(),
                            productosAdapter.getCantidades());

                    mensajeCall.enqueue(new Callback<Mensaje>() {
                        @Override
                        public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                            if(response.isSuccessful()){
                                Obra obra = new Obra(nombre, tipo, descripcion);
                                dbManager.insertarModelo(obra);

                                Toast.makeText(AgregarObraActivity.this, "Obra guardada exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                btnGuardarObra.setVisibility(View.VISIBLE);
                                progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Mensaje> call, Throwable t) {
                            Toast.makeText(AgregarObraActivity.this, "Verifique su conexion y vuelva a intentar", Toast.LENGTH_SHORT).show();

                            btnGuardarObra.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case COD_ADD_PRODUCTO:
                    Producto producto = (Producto) data.getSerializableExtra(ProductosActivity.ARG_PRODUCTO);

                    if(producto != null){
                        productosAdapter.agregarProducto(producto);

                        txtNoTieneProd.setVisibility(View.GONE);
                    }

                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
