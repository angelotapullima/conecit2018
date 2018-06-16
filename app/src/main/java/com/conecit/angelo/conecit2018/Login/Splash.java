package com.conecit.angelo.conecit2018.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.conecit.angelo.conecit2018.MainActivity;
import com.conecit.angelo.conecit2018.R;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    private TextView textsplash;
    private ImageView imgsplash;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();


        textsplash=(TextView)findViewById(R.id.textsplash);
        imgsplash=(ImageView)findViewById(R.id.imgsplash);
        Animation myanin = AnimationUtils.loadAnimation(this,R.anim.mysplash);
        textsplash.startAnimation(myanin);
        imgsplash.startAnimation(myanin);
        final Intent i = new Intent(this,LoginActivity.class);
        final Intent in = new Intent(this,MainActivity.class);

            Thread timer = new Thread(){
                public void run (){
                    try{
                        sleep(2500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        if (auth.getCurrentUser() !=null){

                            startActivity(in);
                            finish();
                        }else{
                            startActivity(i);
                            finish();
                        }

                        finish();

                    }

                }
            };
            timer.start();
    }

}
