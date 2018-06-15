package com.conecit.angelo.conecit2018;


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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CrearCuenta extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrar;
    TextView btnlogin , btnContraeña_olvidada;
    EditText ingresar_email, ingresar_contraseña;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        btnRegistrar = findViewById(R.id.signup_btn_register);
        btnlogin = findViewById(R.id.signup_btn_login);
        btnContraeña_olvidada = findViewById(R.id.signup_btn_forgot_pass);
        ingresar_email = findViewById(R.id.signup_email);
        ingresar_contraseña = findViewById(R.id.signup_password);
        activity_sign_up = findViewById(R.id.activity_sign_up);


        btnlogin.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnContraeña_olvidada.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.signup_btn_login)
        {
            startActivity(new Intent(CrearCuenta.this,LoginActivity.class));
            finish();
        }
        else if (v.getId()==R.id.signup_btn_forgot_pass)
        {
            startActivity(new Intent(CrearCuenta.this,Forgot_Password.class));
            finish();
        }else if (v.getId()==R.id.signup_btn_register)
        {
            signUpUser(ingresar_email.getText().toString(),ingresar_contraseña.getText().toString());
        }

    }

    private void signUpUser(String email, String contraseña) {
        auth.createUserWithEmailAndPassword(email,contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            snackbar = Snackbar.make(activity_sign_up,"Error: "+task.getException(),Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else {
                            snackbar = Snackbar.make(activity_sign_up,"Registro exitoso: ",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    }
                });
    }
}
