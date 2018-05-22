package miercoles.dsl.modulo2.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.modelos.Presupuesto;

public class PresupuestosAdapter extends RecyclerView.Adapter<PresupuestosAdapter.PresupuestosViewHolder>{
    public interface ListenerClick {
        void longClickItem(Presupuesto presupuesto, int posicion);
    }

    ArrayList<Presupuesto> presupuestos;
    private ListenerClick miListener;

    public PresupuestosAdapter(ArrayList<Presupuesto> presupuestos, ListenerClick miListener) {
        this.presupuestos = presupuestos;
        this.miListener = miListener;
    }

    public class PresupuestosViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private final TextView txtPrecio, txtFecha, txtHora;

        public PresupuestosViewHolder(View itemView) {
            super(itemView);

            txtPrecio = itemView.findViewById(R.id.item_precio_presupuesto);
            txtFecha = itemView.findViewById(R.id.item_fecha_presupuesto);
            txtHora = itemView.findViewById(R.id.item_hora_presupuesto);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            switch (v.getId()){
                case R.id.item_presupuesto:
                    if(miListener != null){
                         miListener.longClickItem( presupuestos.get( getAdapterPosition() ), getAdapterPosition() );
                    }
                    return true;
            }

            return false;
        }
    }

    @NonNull
    @Override
    public PresupuestosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_presupuesto, parent, false);

        return new PresupuestosViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PresupuestosViewHolder holder, int position) {
        Presupuesto presupuesto = presupuestos.get( position );

        try {
            String fechaCompleta = presupuesto.getFecha();

            String fecha = fechaCompleta.split(" ")[0];
            String hora = fechaCompleta.split(" ")[1];

            holder.txtFecha.setText(fecha);
            holder.txtHora.setText(hora);
            holder.txtPrecio.setText( presupuesto.getPrecio() + "" );

        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return presupuestos.size();
    }

    public ArrayList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
        notifyDataSetChanged();
    }
}
