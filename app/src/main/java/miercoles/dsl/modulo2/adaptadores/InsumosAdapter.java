package miercoles.dsl.modulo2.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.modelos.Insumo;

public class InsumosAdapter extends RecyclerView.Adapter<InsumosAdapter.InsumosViewHolder>{


    public interface ListenerClick{
        void clickItem(Insumo insumo, int posicion);
    }

    private ListenerClick miListener;
    private ArrayList<Insumo> insumos;

    public class InsumosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView txtCantidad, txtNombre, txtDescripcion;

        public InsumosViewHolder(View itemView) {
            super(itemView);

            txtCantidad = itemView.findViewById(R.id.item_txt_cantidad_insumo);
            txtNombre = itemView.findViewById(R.id.item_nombre_insumo);
            txtDescripcion = itemView.findViewById(R.id.item_descripcion_insumo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_insumo:
                    if(miListener != null){
                        miListener.clickItem( insumos.get( getAdapterPosition() ), getAdapterPosition() );
                    }
                    break;
            }
        }
    }

    @NonNull
    @Override
    public InsumosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_insumo, parent, false);

        return new InsumosViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull InsumosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return insumos.size();
    }
}
