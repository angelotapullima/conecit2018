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

import com.conecit.angelo.conecit2018.model.InscritosTalleres;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InscripcionTallerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_taller);
        showToolbar("Inscripcion a Talleres",true);

        final FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = databaseReference.getReference("Talleres");

        final TextInputEditText etemail = (TextInputEditText)findViewById(R.id.itemail);
        final TextInputEditText etname = (TextInputEditText)findViewById(R.id.itname);
        final TextInputEditText etuser = (TextInputEditText)findViewById(R.id.ituser);
        final TextInputEditText etpassword = (TextInputEditText)findViewById(R.id.itpas);
        final TextInputEditText etconfirm = (TextInputEditText)findViewById(R.id.confirmpassword);

        Button btnUs = (Button)findViewById(R.id.Us);
        btnUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mdialog = new ProgressDialog(InscripcionTallerActivity.this);
                mdialog.setMessage("Cargando...");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(etuser.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(InscripcionTallerActivity.this,"Usuario ya registrado ",Toast.LENGTH_SHORT).show();
                        } else {
                            mdialog.dismiss();
                            InscritosTalleres inscritos = new InscritosTalleres(etemail.getText().toString(),etuser.getText().toString(),etname.getText().toString(),etpassword.getText().toString());
                            table_user.child(etname.getText().toString()).child(etuser.getText().toString()).setValue(inscritos);
                            Toast.makeText(InscripcionTallerActivity.this,"Usuario registrado",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(InscripcionTallerActivity.this,MainActivity.class);
                            startActivity(i);

                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
