package miercoles.dsl.modulo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.adaptadores.ProductosAdapter;
import miercoles.dsl.modulo2.modelos.Producto;

public class AgregarObraActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtNombre, edtDescripcion;
    private Spinner spnTipo;
    private RecyclerView recyclerProductos;
    private ProductosAdapter productosAdapter;
    private TextView txtNoTieneProd;
    private Button btnGuardarObra, btnAgregarTrabajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_obra);
        setToolbar();

        txtNoTieneProd = findViewById(R.id.txt_no_tiene_prod);
        edtNombre = findViewById(R.id.edt_nombre_obra);
        edtDescripcion = findViewById(R.id.edt_descripcion_obra);
        spnTipo = findViewById(R.id.spn_tipo_obra);
        btnAgregarTrabajo = findViewById(R.id.btn_agregar_producto);
        btnGuardarObra = findViewById(R.id.btn_guardar_obra);

        recyclerProductos = findViewById(R.id.recycler_productos_obra);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProductos.setLayoutManager(layoutManager);

        productosAdapter = new ProductosAdapter(new ArrayList<Producto>(),
                                    new EscuchadorClick(), ProductosAdapter.TIPO_CANTIDAD);

        btnAgregarTrabajo.setOnClickListener(this);
        btnGuardarObra.setOnClickListener(this);
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

                break;
            case R.id.btn_guardar_obra:

                break;
        }
    }
}
