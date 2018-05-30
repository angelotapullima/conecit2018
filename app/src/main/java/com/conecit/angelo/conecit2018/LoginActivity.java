package com.conecit.angelo.conecit2018;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
    private SharedPreferences pref;
    TextInputEditText lopassword;
    TextInputEditText lousername;
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //firebaseAuth = FirebaseAuth.getInstance();
        pref= getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        final FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = databaseReference.getReference("Usuarios");

        lopassword = (TextInputEditText)findViewById(R.id.edtpassword);
        lousername = (TextInputEditText)findViewById(R.id.edtuser);
        btnLogin=(Button)findViewById(R.id.login);

        setCredentialIfExist();
        //progressBar = (ProgressBar)findViewById(R.id.progresBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(LoginActivity.this);
                mdialog.setMessage("Cargando...");
                mdialog.show();
                final String user = lousername.getText().toString().trim();

                final String password = lopassword.getText().toString().trim();

                if(user.equals("")){
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
                        if (dataSnapshot.child(user).exists())
                        {
                            mdialog.dismiss();
                            Usuarios usuarios = dataSnapshot.child(user).getValue(Usuarios.class);
                            if (usuarios.getContraseña().equals(password))
                            {
                                Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                SavePreference(user,password);

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

            }
        });
    }

    private void setCredentialIfExist() {
        String user = getUser();
        String password = getPass();
        if (!TextUtils.isEmpty(user)&& !TextUtils.isEmpty(password)){
            lousername.setText(user);
            lopassword.setText(password);
        }
    }

    private void SavePreference(String email, String password){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user",email);
        editor.putString("pass",password);
        editor.apply();

    }

    private String getUser(){
        return pref.getString("user","");
    }
    private String getPass(){
        return pref.getString("pass","");
    }




    public void goCreateAccount(View view){
        Intent intent = new Intent(this,CrearCuenta.class);
        startActivity(intent);


    }
}
