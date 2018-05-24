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
import com.conecit.angelo.conecit2018.Details.DetailsConcursos;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.model.DatosConcursos;

import java.util.ArrayList;

public class ConcursosAdapterRecyclerview extends RecyclerView.Adapter<ConcursosAdapterRecyclerview.Concursosviewholder> {
    private ArrayList<DatosConcursos> listaConcursos;
    private Activity activityConcursos;
    private Context context;
    private RequestOptions options ;
    //private RequestQueue request;

    public ConcursosAdapterRecyclerview(ArrayList<DatosConcursos> listaConcursos, Activity activityConcursos, Context context) {
        this.listaConcursos = listaConcursos;
        this.activityConcursos = activityConcursos;
        this.context = context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image)
                .error(R.drawable.image);
        //request= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public ConcursosAdapterRecyclerview.Concursosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_concursos,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new Concursosviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcursosAdapterRecyclerview.Concursosviewholder holder, final int position) {
        holder.tituloconcursos.setText(listaConcursos.get(position).getTituloconcursos());
        Glide.with(context).load(listaConcursos.get(position).getImagenconcursos()).apply(options).into(holder.imagenconcursos);
        holder.imagenconcursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activityConcursos, DetailsConcursos.class);
                i.putExtra("tituloConcursos",listaConcursos.get(position).getTituloconcursos());
                i.putExtra("descripcionConcursos",listaConcursos.get(position).getDescripcionconcursos());
                i.putExtra("imagenConcursos",listaConcursos.get(position).getImagenconcursos());

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activityConcursos.getWindow().setExitTransition(explode);
                    activityConcursos.startActivity(i,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activityConcursos,v,activityConcursos.getString(R.string.transition_imagentalleres)).toBundle());

                }else{
                    activityConcursos.startActivity(i);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaConcursos.size();
    }

    public class Concursosviewholder extends RecyclerView.ViewHolder {
        private TextView tituloconcursos ;
        private ImageView imagenconcursos;
        public Concursosviewholder(View view) {
            super(view);
            tituloconcursos       =view.findViewById(R.id.tituloconcursosCard);
            imagenconcursos         =view.findViewById(R.id.imagenconcursosCard);
        }
    }
}
