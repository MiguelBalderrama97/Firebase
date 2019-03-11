package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EspecificMissingDog extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ImageView imgEspe;
    private TextView txtEspeRaza, txtEspeFecha, txtEspeCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_missing_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgEspe = findViewById(R.id.imgEspe);
        txtEspeCol = findViewById(R.id.txtEspeCol);
        txtEspeFecha = findViewById(R.id.txtEspeFecha);
        txtEspeRaza = findViewById(R.id.txtEspeRaza);

        Intent inDatos = getIntent();
        Bundle bDatos = inDatos.getExtras();

        final String ID = bDatos.getString("ID");

        DatabaseReference reference = database.getReference("perros").child(ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                int img = dataSnapshot.child("icon").getValue(Integer.class);
                String raza = dataSnapshot.child("raza").getValue(String.class);
                String colonia = dataSnapshot.child("colonia").getValue(String.class);
                String fecha = dataSnapshot.child("fecha").getValue(String.class);

                getSupportActionBar().setTitle(nombre);
                imgEspe.setImageResource(img);
                txtEspeCol.setText(colonia);
                txtEspeFecha.setText(fecha);
                txtEspeRaza.setText(raza);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
