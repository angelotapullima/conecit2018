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

import com.conecit.angelo.conecit2018.MainActivity;
import com.conecit.angelo.conecit2018.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText ingresar_email , ingresar_contraseña;
    TextView btnRegistrar , btnContraseña_olvidada;
    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login_btn_login);
        ingresar_email = (EditText)findViewById(R.id.login_email);
        ingresar_contraseña = (EditText)findViewById(R.id.login_password);
        btnRegistrar = (TextView) findViewById(R.id.login_btn_signup);
        btnContraseña_olvidada = (TextView) findViewById(R.id.login_btn_forgot_password);
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);


        btnRegistrar.setOnClickListener(this);
        btnContraseña_olvidada.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn_forgot_password)
        {
            startActivity(new Intent(LoginActivity.this,Forgot_Password.class));
            finish();
        }
        else if (v.getId()==R.id.login_btn_signup)
        {
            startActivity(new Intent(LoginActivity.this,CrearCuenta.class));
            finish();
        }else if (v.getId()==R.id.login_btn_login)
        {
            loginUser(ingresar_email.getText().toString(),ingresar_contraseña.getText().toString());
        }

    }

    private void loginUser(String email, final String contra) {
        auth.signInWithEmailAndPassword(email,contra)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            if (contra.length()<6)
                            {
                                Snackbar snackbar = Snackbar.make(activity_main,"la contraseña debe tener más de 6 caracteres",Snackbar.LENGTH_SHORT);
                            }
                        } else
                        {

                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }

                    }
                });


    }
}
