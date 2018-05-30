package com.conecit.angelo.conecit2018;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.conecit.angelo.conecit2018.model.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = databaseReference.getReference("Usuarios");

        final TextInputEditText lopassword = (TextInputEditText)findViewById(R.id.edtpassword);
        final TextInputEditText lousername = (TextInputEditText)findViewById(R.id.edtuser);
        btnLogin=(Button)findViewById(R.id.login);
        //progressBar = (ProgressBar)findViewById(R.id.progresBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(LoginActivity.this);
                mdialog.setMessage("Cargando...");
                mdialog.show();
                String email = lousername.getText().toString().trim();

                String password = lopassword.getText().toString().trim();

                if(email.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresar email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.equals("")){

                        Toast.makeText(getApplicationContext(),"Ingresar Correo",Toast.LENGTH_LONG).show();

                    return;
                }
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(lousername.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Usuarios usuarios = dataSnapshot.child(lousername.getText().toString()).getValue(Usuarios.class);
                            if (usuarios.getContraseña().equals(lopassword.getText().toString()))
                            {
                                Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);

                            }else
                            {
                                Toast.makeText(LoginActivity.this,"Ingreso fallido",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(LoginActivity.this,"Usuario no existe",Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                /*firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }else
                        {

                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    }
                });*/

            }
        });
    }



    public void goCreateAccount(View view){
        Intent intent = new Intent(this,CrearCuenta.class);
        startActivity(intent);


    }
}
