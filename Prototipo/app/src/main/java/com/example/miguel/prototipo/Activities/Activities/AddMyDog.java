package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMyDog extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_dog);

        getSupportActionBar().setTitle("NUEVO PERRO");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perro perro = new Perro(R.mipmap.ic_perro2,5,"Perrito","Chihuahua","Mike",true);
                perro.setColonia("Colinas del Sol");
                perro.setFecha("19 de marzo");
                perro.setImg1(R.mipmap.ic_perro);
                perro.setImg2(R.mipmap.ic_perro3);
                perro.setIm3(R.mipmap.ic_perro4);
                reference.push().setValue(perro);
            }
        });
    }

}
