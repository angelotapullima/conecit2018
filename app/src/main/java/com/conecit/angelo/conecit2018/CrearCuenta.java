package com.conecit.angelo.conecit2018;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.conecit.angelo.conecit2018.model.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearCuenta extends AppCompatActivity {


    //DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount),true);

        final FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = databaseReference.getReference("Usuarios");

        final TextInputEditText etemail = (TextInputEditText)findViewById(R.id.caemail);
        final TextInputEditText etname = (TextInputEditText)findViewById(R.id.caname);
        final TextInputEditText etuser = (TextInputEditText)findViewById(R.id.edtuser);
        final TextInputEditText etpassword = (TextInputEditText)findViewById(R.id.edtpassword);
        final TextInputEditText etconfirm = (TextInputEditText)findViewById(R.id.confirmpassword);

        Button btnCreateAccount = (Button)findViewById(R.id.joinUs);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mdialog = new ProgressDialog(CrearCuenta.this);
                mdialog.setMessage("Cargando...");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(etuser.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(CrearCuenta.this,"Usuario ya registrado ",Toast.LENGTH_SHORT).show();
                        } else {
                            mdialog.dismiss();
                            Usuarios usuario = new Usuarios(etemail.getText().toString(),etuser.getText().toString(),etname.getText().toString(),etpassword.getText().toString());
                            table_user.child(etuser.getText().toString()).setValue(usuario);
                            Toast.makeText(CrearCuenta.this,"Usuario registrado",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CrearCuenta.this,LoginActivity.class);
                            startActivity(i);

                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*String nombre = etname.getText().toString().trim();
                String usuario = etuser.getText().toString().trim();
                String correo = etemail.getText().toString().trim();
                String contraseña = etpassword.getText().toString().trim();
                String confirm = etconfirm.getText().toString().trim();

                Usuarios datos = new Usuarios(nombre,usuario,correo,contraseña);
                //databaseReference.child("Usuarios").child(usuario).setValue(datos);


                /*if(!contraseña.equals(confirm)){
                    if (contraseña.equals("")){

                    }
                        //Toast.makeText(getApplicationContext(),"Contraseñas Invalida",Toast.LENGTH_LONG).show();
                    }
                    //return;




                /*if(email.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresar email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(confirm)){
                    if (password.equals("")){
                        Toast.makeText(getApplicationContext(),"Contraseñas Invalida",Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CrearCuenta.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(CrearCuenta.this,"Auth Failed",Toast.LENGTH_LONG).show();
                        }else{
                            startActivity(new Intent(CrearCuenta.this,LoginActivity.class));
                            finish();
                        }
                    }
                });*/


            }
        });

    }


    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


}
