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

public class DetailsConcursos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_concursos);
        showToolbar("",true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());

        }


        String descripcionconcur = getIntent().getExtras().getString("descripcionConcursos");
        String tituloconcur = getIntent().getExtras().getString("tituloConcursos");
        String imagenconcur = getIntent().getExtras().getString("imagenConcursos");



        TextView tituloConcursos = findViewById(R.id.tituloConcursosDt);
        TextView descripcionConcursos = findViewById(R.id.descripcionConcursosDt);
        ImageView imagenConcursos = findViewById(R.id.imagenConcursosDt);


        tituloConcursos.setText(tituloconcur);
        descripcionConcursos.setText(descripcionconcur);
        RequestOptions requestOptions= new RequestOptions().centerCrop().placeholder(R.drawable.image).error(R.drawable.image);

        Glide.with(this).load(imagenconcur).apply(requestOptions).into(imagenConcursos);

    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarCt);

    }
}
