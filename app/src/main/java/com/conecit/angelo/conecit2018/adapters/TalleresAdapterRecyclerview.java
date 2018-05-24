package com.conecit.angelo.conecit2018.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.conecit.angelo.conecit2018.Details.DetailsTaller1;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.model.DatosTalleres;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TalleresAdapterRecyclerview extends RecyclerView.Adapter<TalleresAdapterRecyclerview.Talleresviewholder> {
    private ArrayList<DatosTalleres> listaTalleres;
    private Activity activityTaller;
    private Context context;
    private RequestOptions options ;
    //private RequestQueue request;

    public TalleresAdapterRecyclerview(ArrayList<DatosTalleres> listaTalleres, Activity activityTaller, Context context) {
        this.listaTalleres = listaTalleres;
        this.context=context;
        this.activityTaller = activityTaller;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image)
                .error(R.drawable.image);
        //request=Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public Talleresviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_talleres,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new Talleresviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Talleresviewholder holder, final int position) {
        holder.tituloTalleresCard.setText(listaTalleres.get(position).getTituloTaller());
        holder.ponenteTallerCard.setText(listaTalleres.get(position).getPonenteTaller());
        holder.duracionTalleresCard.setText(listaTalleres.get(position).getDuracionTaller());
        holder.precioTallerCard.setText(listaTalleres.get(position).getPrecioTaller());
        Glide.with(context).load(listaTalleres.get(position).getImagenTalleres()).apply(options).into(holder.imagentalleresCard);
        //Picasso.with(activityTaller).load(listaTalleres.get(position).getImagenTalleres()).into(holder.imagentalleresCard);

        holder.imagentalleresCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activityTaller, DetailsTaller1.class);
                i.putExtra("tituloTaller",listaTalleres.get(position).getTituloTaller());
                i.putExtra("ponenteTaller",listaTalleres.get(position).getPonenteTaller());
                i.putExtra("precioTaller",listaTalleres.get(position).getPrecioTaller());
                i.putExtra("duracionTaller",listaTalleres.get(position).getDuracionTaller());
                i.putExtra("descripcionTaller",listaTalleres.get(position).getDescripcionTaller());
                i.putExtra("imagenTaller",listaTalleres.get(position).getImagenTalleres());
                i.putExtra("fotoPonente",listaTalleres.get(position).getFotoPonente());

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activityTaller.getWindow().setExitTransition(explode);
                    activityTaller.startActivity(i,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activityTaller,v,activityTaller.getString(R.string.transition_imagentalleres)).toBundle());

                }else{
                    activityTaller.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listaTalleres.size();
    }

    public class Talleresviewholder extends RecyclerView.ViewHolder {
        private ImageView imagentalleresCard;
        private TextView tituloTalleresCard,ponenteTallerCard,duracionTalleresCard,precioTallerCard;

        public Talleresviewholder(View itemView) {
            super(itemView);

            imagentalleresCard             =(ImageView)itemView.findViewById(R.id.imagentalleresCard);
            tituloTalleresCard       =(TextView)itemView.findViewById(R.id.tituloTalleresCard);
            ponenteTallerCard        =(TextView)itemView.findViewById(R.id.ponenteTallerCard);
            duracionTalleresCard        =(TextView)itemView.findViewById(R.id.duracionTalleresCard);
            precioTallerCard =(TextView)itemView.findViewById(R.id.precioTallerCard);
        }
    }
}
