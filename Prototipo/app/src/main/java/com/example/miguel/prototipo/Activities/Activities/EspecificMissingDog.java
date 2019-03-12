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

import java.util.ArrayList;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;

public class EspecificMissingDog extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ImageView imgEspe;
    private TextView txtEspeRaza, txtEspeFecha, txtEspeCol;

    private EasySlider easySlider;
    private List<SliderItem> sliderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_missing_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        imgEspe = findViewById(R.id.imgEspe);
        txtEspeCol = findViewById(R.id.txtEspeCol);
        txtEspeFecha = findViewById(R.id.txtEspeFecha);
        txtEspeRaza = findViewById(R.id.txtEspeRaza);
        easySlider = findViewById(R.id.slider);

        Intent inDatos = getIntent();
        Bundle bDatos = inDatos.getExtras();

        final String ID = bDatos.getString("ID");

        DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS).child(ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                String raza = dataSnapshot.child("raza").getValue(String.class);
                String colonia = dataSnapshot.child("colonia").getValue(String.class);
                String fecha = dataSnapshot.child("fecha").getValue(String.class);
                int img1 = dataSnapshot.child("icon").getValue(Integer.class);
                int img2 = dataSnapshot.child("img1").getValue(Integer.class);
                int img3 = dataSnapshot.child("img2").getValue(Integer.class);
                int img4 = dataSnapshot.child("im3").getValue(Integer.class);

                sliderItems.add(new SliderItem("Desaparecido",img1));
                sliderItems.add(new SliderItem("Desaparecido",img3));
                sliderItems.add(new SliderItem("Desaparecido",img4));
                sliderItems.add(new SliderItem("Desaparecido",img2));

                easySlider.setPages(sliderItems);
//
                getSupportActionBar().setTitle(nombre.toUpperCase());
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
