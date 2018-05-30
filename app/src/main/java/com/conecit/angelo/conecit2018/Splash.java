package com.conecit.angelo.conecit2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private TextView textsplash;
    private ImageView imgsplash;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref= getSharedPreferences("Preferences", Context.MODE_PRIVATE);

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
                        sleep(4000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        if(!TextUtils.isEmpty(getUser())&& !TextUtils.isEmpty(getPass())){
                            startActivity(in);
                        }else{
                            startActivity(i);
                        }

                        //finish();

                    }

                }
            };
            timer.start();
    }
    private String getUser(){
        return pref.getString("user","");
    }
    private String getPass(){
        return pref.getString("pass","");
    }
}
