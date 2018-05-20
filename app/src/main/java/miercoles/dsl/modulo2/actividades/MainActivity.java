package miercoles.dsl.modulo2.actividades;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.adaptadores.ObrasAdapter;
import miercoles.dsl.modulo2.basedatos.DBManager;
import miercoles.dsl.modulo2.fragments.MiPerfilFragment;
import miercoles.dsl.modulo2.fragments.ObrasFragment;
import miercoles.dsl.modulo2.modelos.Obra;

public class MainActivity extends AppCompatActivity {

    private TextView txtNoTieneObras;
    private RecyclerView recyclerObras;
    private DBManager dbManager;
    private ArrayList<Obra> obras;
    private ObrasAdapter obrasAdapter;
    private AppBarLayout appBar;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private TabsPagerAdapter adapterFragments;

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

        appBar = (AppBarLayout) findViewById(R.id.appbar);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        adapterFragments = new TabsPagerAdapter(getSupportFragmentManager());
        adapterFragments.addFragment(new ObrasFragment(), "OBRAS");
        adapterFragments.addFragment(new MiPerfilFragment(), "PERFIL");
        viewPager.setAdapter(adapterFragments);
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void setTitle(int posFragment, String nuevoTitulo){
            if(posFragment < mFragments.size())// Para no pasarce del tamaño del arraylist
                mFragmentTitles.set(posFragment, nuevoTitulo);
        }

        public Fragment getFragment(int posicion){
            if(posicion < mFragments.size())// Para no pasarce del tamaño del arraylist
                return mFragments.get(posicion);
            else
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
