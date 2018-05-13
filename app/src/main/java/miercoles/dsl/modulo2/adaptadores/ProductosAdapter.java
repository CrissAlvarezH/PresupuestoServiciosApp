package miercoles.dsl.modulo2.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import miercoles.dsl.modulo2.R;
import miercoles.dsl.modulo2.modelos.Producto;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {
    public static final int TIPO_CANTIDAD = 1;
    public static final int TIPO_LISTAR = 2;

    public interface ListenerClick {
        void clickItem(Producto producto, int posicion);
    }

    private ArrayList<Producto> productos;
    private ListenerClick miListener;
    private int tipo;

    public ProductosAdapter(ArrayList<Producto> productos, ListenerClick miListener, int tipo) {
        this.productos = productos;
        this.miListener = miListener;
        this.tipo = tipo;
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtNombre, txtDescripcion, txtPrecio, txtUniMed, txtCantidad;
        private ImageView imgLogo;

        public ProductosViewHolder(View itemView) {
            super(itemView);

            imgLogo = itemView.findViewById(R.id.item_logo_producto);
            txtNombre = itemView.findViewById(R.id.item_nombre_producto);
            txtPrecio = itemView.findViewById(R.id.item_precio_producto);
            txtDescripcion = itemView.findViewById(R.id.item_descripcion_producto);
            txtUniMed = itemView.findViewById(R.id.item_unidad_med_producto);
            txtCantidad = itemView.findViewById(R.id.item_txt_cantidad_producto);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_producto:
                    if(miListener != null)
                        miListener.clickItem( productos.get( getAdapterPosition() ), getAdapterPosition() );
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_productos, parent, false);

        return new ProductosViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        Producto producto = productos.get( position );

        holder.txtNombre.setText( producto.getNombre() );
        holder.txtDescripcion.setText( producto.getDescripcion() );
        holder.txtPrecio.setText( "$" + producto.getPrecio() );

        String unidadMedida = producto.getUnidad_medida();
        switch (unidadMedida){
            case "Kilogramo":
                unidadMedida = "Kg";
                break;
            case "metro cubico":
                unidadMedida = "m^3";
                break;
            case "metro cuadrado":
                unidadMedida = "m^2";
                break;
            case "metro":
                unidadMedida = "m";
                break;
        }

        holder.txtUniMed.setText(unidadMedida);

        switch (tipo){
            case TIPO_CANTIDAD:
                holder.imgLogo.setVisibility(GONE);

                String cantidad = producto.getCantidad() + "";
                if(cantidad.length() > 0){
                    if(cantidad.charAt( cantidad.length() - 1 ) == '0'){
                        // Si el numero tiene un cero al final asumimos que terimina en .0 y se lo quitamos
                        cantidad = cantidad.substring(0, cantidad.length() - 2);
                    }

                    // Si es mayor que cuatro caractes entonces indicamos que no cabe con unos puntos al final
                    if(cantidad.length() > 4)
                        cantidad = cantidad.substring(0, 3) + "..";

                    holder.txtCantidad.setText( cantidad );
                }

                break;
            case TIPO_LISTAR:
                holder.txtCantidad.setText("");
                holder.imgLogo.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    public void agregarProducto(Producto producto){
        productos.add(producto);
        notifyDataSetChanged();
    }
}
