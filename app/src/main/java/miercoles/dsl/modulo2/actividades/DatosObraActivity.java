package miercoles.dsl.modulo2.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.ProductosAdapter;
import miercoles.dsl.modulo2.modelos.Obra;
import miercoles.dsl.modulo2.modelos.Producto;

public class DatosObraActivity extends AppCompatActivity {

    public static final String EXTRA_OBRA = "obra";
    private ArrayList<Producto> productos;
    private TextView txtNombre, txtTipo, txtDescripcion;
    private ProductosAdapter productosAdapter;
    private RecyclerView recyclerProductos;
    private RecyclerView recyclerPresupuestos;
    private TextView txtNoTienePresupuestos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_obra);

        Bundle paquete = getIntent().getExtras();

        if(paquete != null){
            Obra obra = (Obra) paquete.getSerializable(EXTRA_OBRA);

            if(obra != null){
                setToolbar();

                txtNombre = findViewById(R.id.txt_nombre_obra);
                txtTipo = findViewById(R.id.txt_tipo_obra);
                txtDescripcion = findViewById(R.id.txt_descripcion_obra);
                recyclerProductos = findViewById(R.id.recycler_productos_obra);
                txtNoTienePresupuestos = findViewById(R.id.txt_no_tiene_presuspuestos);
                recyclerPresupuestos = findViewById(R.id.recycler_presupuestos_obra);

                RecyclerView.LayoutManager lmProductos = new LinearLayoutManager(this);
                recyclerProductos.setLayoutManager(lmProductos);

                RecyclerView.LayoutManager lmPresupuestos = new LinearLayoutManager(this);
                recyclerPresupuestos.setLayoutManager(lmPresupuestos);

                productos = obra.getProductos();

                txtNombre.setText( obra.getNombre() );
                txtTipo.setText( obra.getTipo() );
                txtDescripcion.setText( obra.getDescripcion() );

                if(productos != null) {
                    productosAdapter = new ProductosAdapter(productos, null, ProductosAdapter.TIPO_CANTIDAD);

                    recyclerProductos.setAdapter(productosAdapter);
                }
            }
        }else{
            finish();
            Toast.makeText(this, "Obra no definida", Toast.LENGTH_SHORT).show();
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
}
