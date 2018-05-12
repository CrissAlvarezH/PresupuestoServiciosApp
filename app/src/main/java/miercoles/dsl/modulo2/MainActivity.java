package miercoles.dsl.modulo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import miercoles.dsl.modulo2.adaptadores.ObrasAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.modelos.Obra;

public class MainActivity extends AppCompatActivity {

    private TextView txtNoTieneObras;
    private RecyclerView recyclerObras;
    private DBManager dbManager;
    private ArrayList<Obra> obras;
    private ObrasAdapter obrasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);

        if(dbManager.getUsuario() == null){
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
            return;
        }

        txtNoTieneObras = findViewById(R.id.txt_no_tiene_obras);
        recyclerObras = findViewById(R.id.recycler_obras);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerObras.setLayoutManager(layoutManager);

        obras = dbManager.getObras();

        if(obras.size() > 0){
            txtNoTieneObras.setVisibility(View.GONE);
        }

        obrasAdapter = new ObrasAdapter(obras, new MiListener());

        recyclerObras.setAdapter(obrasAdapter);
    }

    private class MiListener implements ObrasAdapter.ListenerClick{

        @Override
        public void clickItem(Obra obra, int posicion) {
            Toast.makeText(MainActivity.this, "Click en "+obra.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

}
