package com.example.miguel.prototipo.Activities.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMyDog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerRaza;
    private ImageButton btnImg1, btnImg2, btnImg3, btnImg4;
    private ImageView icon1, icon2, icon3, icon4;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private Intent inCamera = new Intent("android.media.action.IMAGE_CAPTURE");

    private Bitmap img1, img2, img3, img4;

    private final int PICTURE_FROM_CAMERA = 50;
    private final int PICTURE_FROM_CAMERA2 = 100;
    private final int PICTURE_FROM_CAMERA3 = 150;
    private final int PICTURE_FROM_CAMERA4 = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_dog);

        btnImg1 = findViewById(R.id.btnImg1);
        btnImg2 = findViewById(R.id.btnImg2);
        btnImg3 = findViewById(R.id.btnImg3);
        btnImg4 = findViewById(R.id.btnImg4);

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);

        spinnerRaza = findViewById(R.id.spinnerRaza);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.razas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRaza.setAdapter(adapter);

        spinnerRaza.setOnItemSelectedListener(this);

        getSupportActionBar().setTitle("Agregar a mis perros...");

        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(inCamera, PICTURE_FROM_CAMERA);
            }
        });
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(inCamera, PICTURE_FROM_CAMERA2);
            }
        });
        btnImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(inCamera, PICTURE_FROM_CAMERA3);
            }
        });
        btnImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(inCamera, PICTURE_FROM_CAMERA4);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perro perro = new Perro(R.mipmap.ic_perro,2,
                        R.mipmap.ic_perro2,R.mipmap.ic_perro3,R.mipmap.ic_perro4,
                        "Black","Bulldog","Mike",true);
                reference.push().setValue(perro);
                Toast.makeText(AddMyDog.this, "Perro agregado!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PICTURE_FROM_CAMERA:
                img1 = takePhoto(resultCode,data,icon1);
                break;
            case PICTURE_FROM_CAMERA2:
                img2 = takePhoto(resultCode,data,icon2);
                break;
            case PICTURE_FROM_CAMERA3:
                img3 = takePhoto(resultCode,data,icon3);
                break;
            case PICTURE_FROM_CAMERA4:
                img4 = takePhoto(resultCode,data,icon4);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Bitmap takePhoto(int resultCode, Intent data, ImageView icon){
        if (resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            icon.setImageResource(R.mipmap.ic_checked);
            return photo;
        } else {
            Toast.makeText(this, "Foto cancelada", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position)+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
