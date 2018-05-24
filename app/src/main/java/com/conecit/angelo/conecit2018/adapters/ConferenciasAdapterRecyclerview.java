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
import com.conecit.angelo.conecit2018.Details.DetailsConferencias;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.model.DatosConferencias;

import java.util.ArrayList;

public class ConferenciasAdapterRecyclerview extends RecyclerView.Adapter<ConferenciasAdapterRecyclerview.Conferenciasviewholder> {

    private ArrayList<DatosConferencias> listaConferencias;
    private Activity activityConferencias;
    private Context context;
    private RequestOptions options ;
    //private RequestQueue request;

    public ConferenciasAdapterRecyclerview(ArrayList<DatosConferencias> listaConferencias,  Activity activityConferencias , Context context) {
        this.listaConferencias = listaConferencias;
        this.activityConferencias = activityConferencias;
        this.context=context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image)
                .error(R.drawable.image);
        //request= Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public ConferenciasAdapterRecyclerview.Conferenciasviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_conferencias,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new Conferenciasviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConferenciasAdapterRecyclerview.Conferenciasviewholder holder, final int position) {

        holder.tituloconfeCard.setText(listaConferencias.get(position).getTituloconfe());
        holder.ponenteconfeCard.setText(listaConferencias.get(position).getPonenteconfe());
        Glide.with(context).load(listaConferencias.get(position).getImagenconfe()).apply(options).into(holder.imageconfeCard);
        holder.imageconfeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activityConferencias, DetailsConferencias.class);
                i.putExtra("ponenteConferencias",listaConferencias.get(position).getPonenteconfe());
                i.putExtra("tituloConferencias",listaConferencias.get(position).getTituloconfe());
                i.putExtra("shortConferencias",listaConferencias.get(position).getShortconfe());
                i.putExtra("imagenConferencias",listaConferencias.get(position).getImagenconfe());
                i.putExtra("descripcionConferencias",listaConferencias.get(position).getDescripcionconfe());

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activityConferencias.getWindow().setExitTransition(explode);
                    activityConferencias.startActivity(i,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activityConferencias,v,activityConferencias.getString(R.string.transition_imagentalleres)).toBundle());

                }else{
                    activityConferencias.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaConferencias.size();
    }

    public class Conferenciasviewholder extends RecyclerView.ViewHolder {
        private ImageView imageconfeCard;
        private TextView tituloconfeCard,ponenteconfeCard;

        public Conferenciasviewholder(View view) {
            super(view);
            imageconfeCard             =(ImageView)view.findViewById(R.id.imageconfeCard);
            tituloconfeCard       =(TextView)view.findViewById(R.id.tituloconfeCard);
            ponenteconfeCard        =(TextView)view.findViewById(R.id.ponenteconfeCard);

        }
    }
}
