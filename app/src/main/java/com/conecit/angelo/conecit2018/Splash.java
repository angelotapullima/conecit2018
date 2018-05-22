package com.conecit.angelo.conecit2018;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private TextView textsplash;
    private ImageView imgsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textsplash=(TextView)findViewById(R.id.textsplash);
        imgsplash=(ImageView)findViewById(R.id.imgsplash);
        Animation myanin = AnimationUtils.loadAnimation(this,R.anim.mysplash);
        textsplash.startAnimation(myanin);
        imgsplash.startAnimation(myanin);
        final Intent i = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();

    }
}
