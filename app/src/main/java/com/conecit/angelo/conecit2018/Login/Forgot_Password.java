package com.conecit.angelo.conecit2018.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.conecit.angelo.conecit2018.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity implements View.OnClickListener {

    private EditText ingresar_email;
    private Button btnReset;
    private TextView btnBack;
    private RelativeLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        ingresar_email = findViewById(R.id.forgot_email);
        btnReset = findViewById(R.id.forgot_btn_reset);
        btnBack = findViewById(R.id.forgot_btn_back);
        activity_forgot = findViewById(R.id.activity_forgot);

        btnReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.forgot_btn_back)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else if (v.getId()== R.id.forgot_btn_reset)
        {
            resetPassword(ingresar_email.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot,"hemos enviado la contraseña al correo electrónico: " + email,Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }else
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot,"Fallo en el envio de Contraseña: " ,Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
    }
}
