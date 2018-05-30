package com.conecit.angelo.conecit2018.Details;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.conecit.angelo.conecit2018.InscripcionTallerActivity;
import com.conecit.angelo.conecit2018.R;

public class DetailsTaller1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_taller1);
        showToolbar("",true);
        FloatBtn();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());

        }

        String ponente = getIntent().getExtras().getString("ponenteTaller");
        String description = getIntent().getExtras().getString("descripcionTaller");
        String precio = getIntent().getExtras().getString("precioTaller");
        String duracion = getIntent().getExtras().getString("duracionTaller");
        String titulo = getIntent().getExtras().getString("tituloTaller");
        String imagen = getIntent().getExtras().getString("imagenTaller");
        String foto = getIntent().getExtras().getString("fotoPonente");


        TextView ponenteTaller = findViewById(R.id.usernameDt);
        TextView DuracionTaller = findViewById(R.id.duracionDt);
        TextView precioTaller = findViewById(R.id.precioDt);
        TextView tituloTaller = findViewById(R.id.titleDt);
        TextView descripcionTaller = findViewById(R.id.descripcionDt);
        ImageView imagenTaller = findViewById(R.id.imagenTallerDt);

        ponenteTaller.setText(ponente);
        DuracionTaller.setText(duracion);
        precioTaller.setText(precio);
        tituloTaller.setText(titulo);
        descripcionTaller.setText(description);
        RequestOptions requestOptions= new RequestOptions().centerCrop().placeholder(R.drawable.image).error(R.drawable.image);

        Glide.with(this).load(imagen).apply(requestOptions).into(imagenTaller);


    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarDt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarDt);

    }

    public void FloatBtn (){
        FloatingActionButton fabc = (FloatingActionButton)findViewById(R.id.floatTaller);
        fabc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsTaller1.this, InscripcionTallerActivity.class);
                startActivity(i);

            }
        });
    }
}
