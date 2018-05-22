package miercoles.dsl.modulo2.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.modelos.Obra;

public class ObrasAdapter extends RecyclerView.Adapter<ObrasAdapter.ObrasViewHolder>{
    public interface ListenerClick {
        void clickItem(Obra obra, int posicion);
        void longClickItem(Obra obra, int posicion);
    }

    private ArrayList<Obra> obras;
    private ListenerClick miListener;

    public ObrasAdapter(ArrayList<Obra> obras, ListenerClick miListener) {
        this.obras = obras;
        this.miListener = miListener;
    }

    public class ObrasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private LinearLayout layoutIcono;
        private ImageView imgLogo;
        private TextView txtNombre, txtDescripcion, txtTipo;

        public ObrasViewHolder(View itemView) {
            super(itemView);

            layoutIcono = itemView.findViewById(R.id.item_layout_icono_obra);
            imgLogo = itemView.findViewById(R.id.item_img_icono_obra);
            txtNombre = itemView.findViewById(R.id.item_nombre_obra);
            txtDescripcion = itemView.findViewById(R.id.item_descripcion_obra);
            txtTipo = itemView.findViewById(R.id.item_tipo_obra);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_obra:
                    if(miListener != null){
                        miListener.clickItem( obras.get( getAdapterPosition() ), getAdapterPosition() );
                    }
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.item_obra:
                    if(miListener != null){
                        miListener.longClickItem( obras.get( getAdapterPosition() ), getAdapterPosition() );
                    }
                    return true;
            }


            return false;
        }
    }

    @NonNull
    @Override
    public ObrasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_obras, parent, false);

        return new ObrasViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ObrasViewHolder holder, int position) {
        Obra obra = obras.get(position);

        holder.txtNombre.setText( obra.getNombre() );
        holder.txtDescripcion.setText( obra.getDescripcion() );
        holder.txtTipo.setText( obra.getTipo() );

        int color = R.color.verde;
        int logo = R.drawable.ic_construccion;

        switch (obra.getTipo()){
            case "contruccion":
                color = R.color.verde;
                logo = R.drawable.ic_construccion;
                holder.txtTipo.setText("CONST.");
                break;
            case "remodelacion":
                color = R.color.morado;
                logo = R.drawable.ic_remodelacion;
                holder.txtTipo.setText("REMOD.");
                break;
        }

        holder.layoutIcono.setBackgroundResource(color);
        holder.imgLogo.setImageResource(logo);
    }

    @Override
    public int getItemCount() {
        return obras.size();
    }

    public ArrayList<Obra> getObras() {
        return obras;
    }

    public void setObras(ArrayList<Obra> obras) {
        this.obras = obras;
        notifyDataSetChanged();
    }
}
