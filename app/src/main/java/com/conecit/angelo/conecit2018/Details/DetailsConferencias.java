package com.conecit.angelo.conecit2018.Details;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.conecit.angelo.conecit2018.R;

public class DetailsConferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_conferencias);
        showToolbar("",true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());

        }

        String ponenteconfe = getIntent().getExtras().getString("ponenteConferencias");
        String descriptionconfe = getIntent().getExtras().getString("descripcionConferencias");
        String tituloconfe = getIntent().getExtras().getString("tituloConferencias");
        String imagenconfe = getIntent().getExtras().getString("imagenConferencias");
        String shortbconfe = getIntent().getExtras().getString("shortConferencias");


        TextView ponenteConferencias = findViewById(R.id.ponenteConfereciasDc);
        TextView tituloConferencias = findViewById(R.id.tituloConferenciaDc);
        TextView descripcionConferencias = findViewById(R.id.descripcionConferenciaDc);
        TextView shotConferencias = findViewById(R.id.shortConferenciasDc);

        ImageView imagenConferencias = findViewById(R.id.imagenConferenciasDt);

        ponenteConferencias.setText(ponenteconfe);
        tituloConferencias.setText(tituloconfe);
        descripcionConferencias.setText(descriptionconfe);
        shotConferencias.setText(shortbconfe);
        RequestOptions requestOptions= new RequestOptions().centerCrop().placeholder(R.drawable.image).error(R.drawable.image);

        Glide.with(this).load(imagenconfe).apply(requestOptions).into(imagenConferencias);
    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarCt);

    }
}
